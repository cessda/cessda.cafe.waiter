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

package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.response.ProcessedJobResponse;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import eu.cessda.cafe.waiter.engine.Cashier;
import eu.cessda.cafe.waiter.engine.CoffeeMachine;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Java Engine class to process logic on /collect-jobs end point
 */

@Log4j2
public class JobService {

    private final URL cashierUrl;

    public JobService() {
        try {
            cashierUrl = new URL("http://cafe-cashier:1336/");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public ApiMessage collectJobs() {

        log.info("Collecting jobs from Cashier {}", cashierUrl);

        try {
            var processedJobs = new Cashier(cashierUrl).getProcessedJobs();
            if (log.isTraceEnabled()) log.trace(processedJobs);

            var jobsCollected = 0;
            var jobsNotCollected = 0;

            for (ProcessedJobResponse processedJob : processedJobs) {
                // Start retrieving the job if not retrieved
                var databaseJob = DatabaseClass.job.get(processedJob.getJobId());
                if (databaseJob == null) {
                    var coffeeMachineResponse = new CoffeeMachine(processedJob.getMachine()).retrieveJob(processedJob.getJobId());

                    if (coffeeMachineResponse != null) {
                        var job = new Job();
                        job.setJobId(processedJob.getJobId());
                        job.setJobStarted(processedJob.getJobStarted());
                        job.setProduct(processedJob.getProduct());

                        // Add the job to the persistent data store
                        DatabaseClass.job.put(job.getJobId(), job);

                        // Set the job as collected
                        jobsCollected++;
                    } else {
                        jobsNotCollected++;
                    }
                }
            }
            return ApiMessage.collectJobMessage(jobsCollected, jobsNotCollected);
        } catch (IOException e) {
            log.error("Error connecting to {}: {}", cashierUrl, e.getMessage());
            return new ApiMessage("Error connecting to Cashier " + cashierUrl);
        }
    }
}


