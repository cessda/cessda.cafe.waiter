package eu.cessda.cafe.waiter.data.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.cessda.cafe.waiter.data.model.Job;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderHistoryResponse {
	
    UUID orderId;
    Date orderPlaced;
    int orderSize;
    List<Job> jobs;

}
