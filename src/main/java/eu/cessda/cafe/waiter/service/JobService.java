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

package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.WaiterApplication;
import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.database.Database;
import eu.cessda.cafe.waiter.exceptions.CashierConnectionException;
import eu.cessda.cafe.waiter.helpers.CashierHelper;
import eu.cessda.cafe.waiter.helpers.CoffeeMachineHelper;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Java Engine class to process logic on /collect-jobs end point
 */

@Log4j2
@Service
public class JobService {

    private final URI cashierUri;
    private final CashierHelper cashierHelper;
    private final CoffeeMachineHelper coffeeMachineHelper;
    private final Database database;

    @Inject
    public JobService(CashierHelper cashierHelper, CoffeeMachineHelper coffeeMachineHelper, Database database) {
        this.cashierUri = WaiterApplication.getCashierUrl();
        this.cashierHelper = cashierHelper;
        this.coffeeMachineHelper = coffeeMachineHelper;
        this.database = database;
    }

    public ApiMessage collectJobs() throws CashierConnectionException {

        log.info("Collecting jobs from cashier {}.", cashierUri);

        try {
            var processedJobs = cashierHelper.getProcessedJobs();
            if (log.isTraceEnabled()) log.trace(processedJobs);

            AtomicInteger jobsCollected = new AtomicInteger();
            AtomicInteger jobsNotCollected = new AtomicInteger();

            // Start retrieving the job if not retrieved
            processedJobs.parallelStream().filter(job -> !database.getJob().containsKey(job.getJobId())).forEach(job -> {
                var coffeeMachineResponse = coffeeMachineHelper.retrieveJob(job.getMachine(), job.getJobId());
                if (coffeeMachineResponse != null) {
                    // Copy known variables from the coffee machine
                    job.setJobId(coffeeMachineResponse.getJobId());
                    job.setJobStarted(coffeeMachineResponse.getJobStarted());
                    job.setProduct(coffeeMachineResponse.getProduct());

                    // Set job as retrieved at the current time
                    job.setJobRetrieved(coffeeMachineResponse.getJobRetrieved());

                    // Add the job to the persistent data store
                    database.getJob().put(job.getJobId(), job);

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


