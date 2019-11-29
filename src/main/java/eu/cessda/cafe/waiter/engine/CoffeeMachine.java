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
import eu.cessda.cafe.waiter.data.response.CoffeeMachineResponse;
import eu.cessda.cafe.waiter.helpers.JsonUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Holds methods to talk to remote coffee machines
 */
@Log4j2
public class CoffeeMachine {

    @NonNull
    private final URL coffeeMachineUrl;

    /**
     * Sets up the remote coffee machine
     *
     * @param coffeeMachineUrl The URL of the coffee machine.
     */
    public CoffeeMachine(URL coffeeMachineUrl) {
        this.coffeeMachineUrl = coffeeMachineUrl;
    }

    /**
     * Attempt to retrieve the specified job from the remote coffee machine
     *
     * @param id The UUID of the coffee to retrieve
     * @return The response from the remote coffee machine, or null if an error occurred
     */
    public CoffeeMachineResponse retrieveJob(UUID id) {
        log.info("Retrieving job {} from {}.", id, coffeeMachineUrl);

        try {
            // Set the connection url
            var retrieveJobUrl = new URL(coffeeMachineUrl, "/retrieve-job/" + id);

            // Get the response
            var responseMap = JsonUtils.getObjectMapper().readValue(retrieveJobUrl, CoffeeMachineResponse.class);
            if (log.isTraceEnabled()) log.trace(responseMap);

            return responseMap;

        } catch (MalformedURLException e) {
            throw new IllegalStateException("Malformed URL. This is almost certainly a bug with the application.", e);
        } catch (JsonParseException | JsonMappingException e) {
            log.error("Couldn't parse result from the coffee machine:", e);
        } catch (IOException e) {
            log.error("Error connecting to {}: {}.", coffeeMachineUrl, e.getMessage());
        }
        return null;
    }
}
