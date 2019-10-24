package eu.cessda.cafe.waiter.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessedJob {
    private UUID jobId;
    private String product;
    private UUID orderId;
    private Date orderPlaced;
    private int orderSize;
    private URL machine;
    private String jobStarted;
}
