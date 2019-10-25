package eu.cessda.cafe.waiter.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.cessda.cafe.waiter.data.model.Product;
import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessedJobResponse {
    private UUID jobId;
    private Product product;
    private UUID orderId;
    private Date orderPlaced;
    private int orderSize;
    private URL machine;
    private Date jobStarted;
}
