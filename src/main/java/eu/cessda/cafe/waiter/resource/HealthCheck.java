package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Log4j2
@Path("/healthcheck")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheck {
    @GET
    public ApiMessage getHealth() {
        return ApiMessage.Healthy();
    }
}