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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.response.ProcessedJobResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Cashier {

    private final URL processedJobsEndpoint;

    public Cashier(URL cashierUrl) {
        try {
            processedJobsEndpoint = new URL(cashierUrl, "processed-jobs");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieve a list of processed jobs from the cashier
     *
     * @return List of processed jobs
     * @throws IOException if a problem occurs getting the processed jobs
     */
    public List<ProcessedJobResponse> getProcessedJobs() throws IOException {
        return new ObjectMapper().readValue(processedJobsEndpoint, new TypeReference<List<ProcessedJobResponse>>() {
        });
    }
}
