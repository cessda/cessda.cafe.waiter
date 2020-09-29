/*
 * Copyright CESSDA ERIC 2020.
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

package eu.cessda.cafe.waiter.helpers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.response.CoffeeMachineResponse;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * Holds methods to talk to remote coffee machines
 */
@Log4j2
@Service
public class CoffeeMachineHelper {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Inject
    public CoffeeMachineHelper(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Attempt to retrieve the specified job from the remote coffee machine.
     *
     * @param id The UUID of the coffee to retrieve.
     * @return The response from the remote coffee machine, or null if an error occurred.
     */
    public CoffeeMachineResponse retrieveJob(URI coffeeMachineUrl, UUID id) {
        log.info("Retrieving job {} from {}.", id, coffeeMachineUrl);

        CoffeeMachineResponse responseMap = null;
        String response = null;

        // Set the connection url
        var retrieveJobUrl = coffeeMachineUrl.resolve("retrieve-job/").resolve(id.toString());

        try {
            // Read the response to a string
            var httpRequest = HttpRequest.newBuilder(retrieveJobUrl).build();
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            response = httpResponse.body();

            if (httpResponse.statusCode() < 400) {
                responseMap = objectMapper.readValue(response, CoffeeMachineResponse.class);
            } else {
                throw new IOException("Server returned code " + httpResponse.statusCode());
            }

            // Get the response

            if (log.isTraceEnabled()) log.trace(responseMap);
        } catch (JsonParseException | JsonMappingException e) {
            log.error("Couldn't parse result from the coffee machine:", e);
        } catch (IOException e) {
            // Parse the message from the coffee machine to determine what should be logged
            if (!parseCoffeeMachineResponse(coffeeMachineUrl, response, id)) {
                log.error("Error connecting to {}: {}.", coffeeMachineUrl, e.getMessage());
            }
        } catch (InterruptedException e) {
            log.warn("Interrupted! HTTP request cancelled:", e);
            Thread.currentThread().interrupt();
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
    private boolean parseCoffeeMachineResponse(URI coffeeMachineUrl, String response, UUID id) {
        try {
            if (response != null) {
                var message = objectMapper.readValue(response, ApiMessage.class);
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
            }
        } catch (IOException e) {
            log.error("Response from {} couldn't be parsed.", coffeeMachineUrl, e);
        }
        return false;
    }
}