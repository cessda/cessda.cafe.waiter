package eu.cessda.cafe.waiter.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cessda.cafe.waiter.data.response.ProcessedJobResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Cashier {

    private final URL processedJobsEndpoint;

    public Cashier(URL cashierUrl) {
        try {
            processedJobsEndpoint = new URL(cashierUrl, "processed-jobs");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<ProcessedJobResponse> getProcessedJobs() throws IOException {
        return new ObjectMapper().readValue(processedJobsEndpoint, new TypeReference<List<ProcessedJobResponse>>() {
        });
    }
}
