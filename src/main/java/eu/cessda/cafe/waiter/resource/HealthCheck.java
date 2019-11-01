package eu.cessda.cafe.waiter.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import lombok.extern.log4j.Log4j2;



@Log4j2
@Path("/healthcheck")
public class HealthCheck {

    @GET
    public String getHealth() {
    	log.info("Checking healthcheck status ");
        return "{status:  Healthy }";
    }

}