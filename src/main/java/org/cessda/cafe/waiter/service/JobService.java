package org.cessda.cafe.waiter.service;

import java.util.ArrayList;

/*
 * Java Engine class to process logic on /collect-jobs end point 
 */

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.cessda.cafe.waiter.data.model.JobResponse;
import org.cessda.cafe.waiter.data.model.Machines;
import org.cessda.cafe.waiter.data.model.ProcessedJobs;
import org.cessda.cafe.waiter.message.CollectJobMessage;

public class JobService {

static /*
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
	JobResponse collectjobs = new JobResponse();
	ProcessedJobs processedjobs = new ProcessedJobs();
	Client client = ClientBuilder.newClient();	
	private static final String cashierUrl = machine.getCashier();
	
	
    public  ProcessedJobs getProcessedJobs(){
    	
    	ProcessedJobs response = client
  	          .target(cashierUrl)
  	          .request(MediaType.APPLICATION_JSON)
  	          .header("content-type", MediaType.APPLICATION_JSON)
  	          .get(ProcessedJobs.class);
    	

    	return response;
    		
    }  
    	
    
   
    public String collectJobsMessage(){	
    	
    	
    	int x = 0;
    	int y = 0;
    	
    	CollectJobMessage collectResponse = new CollectJobMessage();		
    	ArrayList<ProcessedJobs> processJob = new ArrayList<ProcessedJobs>();
    	getProcessedJobs();
    	
    	for ( ProcessedJobs jobs : processJob) {
    		boolean isHashcodeEquals = jobs.hashCode() == collectjobs.hashCode();
    		if (isHashcodeEquals) {
    			x++;
    		} else {
    			y++;
    		}          
  }
    	
    	collectResponse.setX(x);
    	collectResponse.setY(y);
    	
    	return collectResponse.toString();

	}
			
	
	        
}


