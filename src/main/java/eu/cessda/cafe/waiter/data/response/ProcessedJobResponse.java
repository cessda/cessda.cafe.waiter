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
    UUID jobId;
    Product product;
    UUID orderId;
    Date orderPlaced;
    int orderSize;
    URL machine;
    Date jobStarted;
}
