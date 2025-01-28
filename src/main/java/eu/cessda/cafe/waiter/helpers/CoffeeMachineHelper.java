/*
 * Copyright CESSDA ERIC 2025.
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.UUID;

/**
 * Holds methods to talk to remote coffee machines
 */
@Service
public class CoffeeMachineHelper {
    private static final Logger log = LogManager.getLogger(CoffeeMachineHelper.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public CoffeeMachineHelper(ObjectMapper objectMapper) {
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

        // Set the connection url
        var retrieveJobUrl = coffeeMachineUrl.resolve("retrieve-job/").resolve(id.toString());

        try {
            // Read the response to a string
            var connection = (HttpURLConnection) retrieveJobUrl.toURL().openConnection();

            try (var inputStream = connection.getInputStream()) {
                // Get the response
                var responseMap = objectMapper.readValue(inputStream, CoffeeMachineResponse.class);
                log.trace(responseMap);
                return responseMap;

            } catch (IOException e) {
                // Parse the message from the coffee machine to determine what should be logged
                if (connection.getErrorStream() != null) {
                    parseCoffeeMachineResponse(coffeeMachineUrl, connection.getErrorStream(), id);
                } else {
                    throw e;
                }
            }

        } catch (JsonParseException | JsonMappingException e) {
            log.error("Couldn't parse result from the coffee machine {}:", coffeeMachineUrl, e);
        } catch (IOException e) {
            log.error("Error connecting to {}: {}.", coffeeMachineUrl, e.toString());
        }
        return null;
    }

    /**
     * Attempt to parse the response from the coffee machine.
     * This method is for logging purposes only and has no effect on waiter function.
     *
     * @param response The response from the coffee machine.
     * @param id       The jobId.
     * @throws IOException if an IO error occurred.
     */
    private void parseCoffeeMachineResponse(URI coffeeMachineUrl, InputStream response, UUID id) throws IOException {
        var message = objectMapper.readValue(response, ApiMessage.class);
        if (message.message().equalsIgnoreCase("job unknown")) {
            log.warn("Job {} is unknown on coffee machine {}.", id, coffeeMachineUrl);
        } else if (message.message().equalsIgnoreCase("job not ready")) {
            log.info("Job {} is not ready on coffee machine {}.", id, coffeeMachineUrl);
        } else if (message.message().equalsIgnoreCase("job already retrieved")) {
            log.warn("Job {} has already been retrieved from coffee machine {}.", id, coffeeMachineUrl);
        } else { // Default handling
            log.warn("Coffee machine {} returned message {}.", coffeeMachineUrl, message.message());
        }
    }
}
