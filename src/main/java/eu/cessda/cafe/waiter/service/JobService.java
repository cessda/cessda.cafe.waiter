package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.model.JobResponse;
import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.data.response.ProcessedJobResponse;
import eu.cessda.cafe.waiter.engine.Cashier;
import eu.cessda.cafe.waiter.engine.CoffeeMachine;
import eu.cessda.cafe.waiter.message.CollectJobMessage;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Java Engine class to process logic on /collect-jobs end point
 */

@Log4j2
public class JobService {

/*
	private Map<String, JobResponse> jobResponse = DatabaseClass.getjobResponse();
	
// MachineService class construct method	
	public JobService() {		
		jobResponse.put("job1", new JobResponse("00000000-2222-2222-2222-000000000000", "KAKAO", "00000000-FFFF-FFFF-FFFF-000000000000", "2019-07-31T01:00:00.000Z", 2,"http://localhost:1337", "2019-07-31T01:00:01.000Z"));
		jobResponse.put("job2", new JobResponse("00000000-1111-1111-1111-000000000000", "KAKAO", "00000000-FFFF-FFFF-FFFF-000000000000", "2019-07-31T01:00:00.000Z", 1,"http://localhost:1337", "2019-07-31T01:00:01.000Z"));
	
	}

/* Engine method to retrieve jobs data from cashier on /processed-jobs
	 * TO BE DONE   	
*/


    /* Returns responses on how many jobs collected from /processed-jobs cashier end points
     * TO BE REVIEWED by Matthew   
*/
    Machines machine = new Machines();
    private final JobResponse collectJobs = new JobResponse();
    private final Client client = ClientBuilder.newClient();
    //private static final String cashierUrl = machine.getCashier();
    private final URL cashierUrl;

    public JobService() {
        try {
            cashierUrl = new URL("http://localhost:5000/");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public ApiMessage collectJobs() {

        int x = 0;
        int y = 0;

        CollectJobMessage collectResponse = new CollectJobMessage();
        try {
            var processedJobs = new Cashier(cashierUrl).getProcessedJobs();
            if (log.isTraceEnabled()) log.trace(processedJobs);
            for (ProcessedJobResponse processedJob : processedJobs) {
                // Start retrieving the job if not retrieved
                var coffeeMachineResponse = new CoffeeMachine(processedJob.getMachine()).retrieveJob(processedJob.getJobId());

                if (coffeeMachineResponse != null) {
                    var job = new Job();
                    job.setJobId(processedJob.getJobId());
                    job.setJobStarted(processedJob.getJobStarted());
                    job.setProduct(processedJob.getProduct());
                }

                boolean isHashcodeEquals = processedJob.hashCode() == collectJobs.hashCode();
                if (isHashcodeEquals) {
                    x++;
                } else {
                    y++;
                }
            }
            collectResponse.setX(x);
            collectResponse.setY(y);
            return new ApiMessage(collectResponse.toString());
        } catch (IOException e) {
            log.error("Error connecting to {}: {}", cashierUrl, e.getMessage());
            return new ApiMessage("Error connecting to Cashier " + cashierUrl);
        }
    }
}


