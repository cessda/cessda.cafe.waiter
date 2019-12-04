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

package eu.cessda.cafe.waiter.data.model;

import lombok.NonNull;
import lombok.Value;

/**
 * Class to hold messages sent to other Coffee API components
 */
@Value
public class ApiMessage {

    /**
     * The message to store
     */
    @NonNull
    String message;

    /**
     * Collected jobs message
     *
     * @param jobsCollected    Jobs collected
     * @param jobsNotCollected Jobs not collected
     * @return The message
     */
    public static ApiMessage collectJobMessage(int jobsCollected, int jobsNotCollected) {
        String message = jobsCollected + " job(s) collected" + ", still waiting for " + jobsNotCollected + " job(s).";
        return new ApiMessage(message);
    }

    public static ApiMessage healthy() {
        return new ApiMessage("Ok");
    }
}
