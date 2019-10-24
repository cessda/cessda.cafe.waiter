package eu.cessda.cafe.waiter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.model.JobResponse;
import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.data.model.ProcessedJobs;
import eu.cessda.cafe.waiter.message.CollectJobMessage;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    ProcessedJobs processedjobs = new ProcessedJobs();
    private final Client client = ClientBuilder.newClient();
    //private static final String cashierUrl = machine.getCashier();
    private final URL processedJobsEndpoint;

    public JobService() {
        try {
            var cashierUrl = new URL("http://localhost:5000/");
            processedJobsEndpoint = new URL(cashierUrl, "processed-jobs");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }


    private ProcessedJobs getProcessedJobs() throws IOException {
        return new ObjectMapper().readValue(processedJobsEndpoint, ProcessedJobs.class);
    }


    public String collectJobsMessage(){


        int x = 0;
        int y = 0;

        CollectJobMessage collectResponse = new CollectJobMessage();
        ArrayList<ProcessedJobs> processJob = new ArrayList<>();
        try {
            var processedJobs = getProcessedJobs();
            for (ProcessedJobs jobs : processJob) {
                boolean isHashcodeEquals = jobs.hashCode() == collectJobs.hashCode();
                if (isHashcodeEquals) {
                    x++;
                } else {
                    y++;
                }
            }
            collectResponse.setX(x);
            collectResponse.setY(y);
            return collectResponse.toString();
        } catch (IOException e) {
            log.error("Connection error:", e);
        }

        return "";

    }


}


