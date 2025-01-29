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

package eu.cessda.cafe.waiter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.Configuration;
import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.database.JobRepository;
import eu.cessda.cafe.waiter.helpers.CoffeeMachineHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Java Engine class to process logic on /collect-jobs end point
 */
@Service
public class JobService {
    private static final Logger log = LogManager.getLogger(JobService.class);

    private final URI cashierUri;
    private final CoffeeMachineHelper coffeeMachineHelper;
    private final JobRepository jobRepository;
    private final ObjectMapper objectMapper;

    private final URI processedJobsEndpoint;

    @Autowired
    public JobService(Configuration configuration, CoffeeMachineHelper coffeeMachineHelper, JobRepository jobRepository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.cashierUri = configuration.cashierUrl();
        this.coffeeMachineHelper = coffeeMachineHelper;
        this.jobRepository = jobRepository;

        processedJobsEndpoint = this.cashierUri.resolve("processed-jobs/");
    }

    public ApiMessage collectJobs() throws CashierConnectionException {

        log.info("Collecting jobs from cashier {}.", cashierUri);

        try {
            log.info("Retrieving all processed jobs from {}.", processedJobsEndpoint);
            var processedJobs = objectMapper.<List<Job>>readValue(processedJobsEndpoint.toURL(), new TypeReference<>() {
            });

            log.trace(() -> processedJobs);

            AtomicInteger jobsCollected = new AtomicInteger();
            AtomicInteger jobsNotCollected = new AtomicInteger();

            // Start retrieving the job if not retrieved
            processedJobs.parallelStream().filter(job -> {
                var localJob = jobRepository.findById(job.getJobId()).orElse(job);
                return localJob.getJobRetrieved() == null;
            }).forEach(job -> {
                var coffeeMachineResponse = coffeeMachineHelper.retrieveJob(job.getMachine(), job.getJobId());
                if (coffeeMachineResponse != null) {
                    // Copy known variables from the coffee machine
                    job.setJobId(coffeeMachineResponse.jobId());
                    job.setJobStarted(coffeeMachineResponse.jobStarted());
                    job.setProduct(coffeeMachineResponse.product());

                    // Set job as retrieved at the current time
                    job.setJobRetrieved(coffeeMachineResponse.jobRetrieved());

                    // Add the job to the persistent data store
                    jobRepository.save(job);

                    // Set the job as collected
                    log.info("Collected job {}.", job.getJobId());
                    jobsCollected.getAndIncrement();
                } else {
                    jobsNotCollected.getAndIncrement();
                }
            });

            return ApiMessage.collectJobMessage(jobsCollected.get(), jobsNotCollected.get());
        } catch (IOException e) { // Send the exception up so a 500 can be generated
            log.error("Error connecting to cashier {}: {}.", cashierUri, e);
            throw new CashierConnectionException(cashierUri, e);
        }
    }
}


