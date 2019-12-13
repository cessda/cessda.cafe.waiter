/*
 * Copyright CESSDA ERIC 2019.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package eu.cessda.cafe.waiter.engine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.response.CoffeeMachineResponse;
import eu.cessda.cafe.waiter.helpers.JsonUtils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Holds methods to talk to remote coffee machines
 */
@Log4j2
public class CoffeeMachine {

    private final URI coffeeMachineUrl;

    /**
     * Sets up the remote coffee machine
     *
     * @param coffeeMachineUrl The URL of the coffee machine.
     */
    public CoffeeMachine(URI coffeeMachineUrl) {
        this.coffeeMachineUrl = coffeeMachineUrl;
    }

    /**
     * Attempt to retrieve the specified job from the remote coffee machine.
     *
     * @param id The UUID of the coffee to retrieve.
     * @return The response from the remote coffee machine, or null if an error occurred.
     */
    public CoffeeMachineResponse retrieveJob(UUID id) {
        log.info("Retrieving job {} from {}.", id, coffeeMachineUrl);

        CoffeeMachineResponse responseMap = null;
        String response = "";

        try {
            // Set the connection url
            var retrieveJobUrl = new URI(coffeeMachineUrl + "retrieve-job/" + id);

            // Read the response to a string
            var httpConn = (HttpURLConnection) retrieveJobUrl.toURL().openConnection();
            if (httpConn.getResponseCode() < 400) {
                response = new String(httpConn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else {
                response = new String(httpConn.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                throw new IOException("Server returned code " + httpConn.getResponseCode());
            }

            // Get the response
            responseMap = JsonUtils.getObjectMapper().readValue(response, CoffeeMachineResponse.class);
            if (log.isTraceEnabled()) log.trace(responseMap);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Malformed URL. This is almost certainly a bug with the application.", e);
        } catch (JsonParseException | JsonMappingException e) {
            log.error("Couldn't parse result from the coffee machine:", e);
        } catch (IOException e) {
            // Parse the message from the coffee machine to determine what should be logged
            if (!parseCoffeeMachineResponse(response, id)) {
                log.error("Error connecting to {}: {}.", coffeeMachineUrl, e.getMessage());
            }
        }
        return responseMap;
    }

    /**
     * Attempt to parse the response from the coffee machine.
     * This method is for logging purposes only and has no effect on waiter function.
     *
     * @param response The response from the coffee machine.
     * @param id       The jobId.
     * @return true if the message was parsed successfully, false otherwise.
     */
    private boolean parseCoffeeMachineResponse(String response, UUID id) {
        try {
            var message = JsonUtils.getObjectMapper().readValue(response, ApiMessage.class);
            if (message.getMessage().equalsIgnoreCase("job unknown")) {
                log.warn("Job {} is unknown on coffee machine {}.", id, coffeeMachineUrl);
            } else if (message.getMessage().equalsIgnoreCase("job not ready")) {
                log.info("Job {} is not ready on coffee machine {}.", id, coffeeMachineUrl);
            } else if (message.getMessage().equalsIgnoreCase("job already retrieved")) {
                log.warn("Job {} has already been retrieved from coffee machine {}.", id, coffeeMachineUrl);
            } else { // Default handling
                log.warn("Coffee machine {} returned message {}.", coffeeMachineUrl, message.getMessage());
            }
            return true;
        } catch (IOException e) {
            log.error("Response from {} couldn't be parsed.", coffeeMachineUrl, e);
        }
        return false;
    }
}
