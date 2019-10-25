package eu.cessda.cafe.waiter.engine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.response.CoffeeMachineResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Log4j2
public class CoffeeMachine {

    private final URL coffeeMachineUrl;

    public CoffeeMachine(URL coffeeMachineUrl) {
        this.coffeeMachineUrl = coffeeMachineUrl;
    }

    public void retrieveOrder(UUID orderId) {
        log.info("Retrieving order: {}", orderId);

        // A foreach loop selecting each job
        var jobId = UUID.fromString("c1be03bf-d9cc-486b-92af-3d91c27d3ba5");
        retrieveJob(jobId);
    }

    public CoffeeMachineResponse retrieveJob(UUID id) {
        // START
        log.info("Retrieving Job {}", id);

        // Get all coffee machines (or better, get the associated coffee machine)
        try {
            // Set the connection url
            log.info("Connecting to coffee machine {}", coffeeMachineUrl);
            var retrieveJobUrl = new URL(coffeeMachineUrl, "/retrieve-job/" + id);

            // Get the response
            var responseMap = new ObjectMapper().readValue(retrieveJobUrl, CoffeeMachineResponse.class);
            if (log.isTraceEnabled()) log.trace(responseMap);

            return responseMap;

        } catch (MalformedURLException e) {
            throw new IllegalStateException("Malformed URL. This is almost certainly a bug with the application.", e);
        } catch (JsonParseException | JsonMappingException e) {
            log.error("Couldn't parse result from the coffee machine:", e);
        } catch (IOException e) {
            log.error("Error connecting to {}: {}", coffeeMachineUrl, e.getMessage());
        }
        return null;
    }
}