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


