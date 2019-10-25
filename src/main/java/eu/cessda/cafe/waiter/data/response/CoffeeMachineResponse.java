package eu.cessda.cafe.waiter.data.response;

import eu.cessda.cafe.waiter.data.model.Product;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CoffeeMachineResponse {
    UUID jobId;
    Product product;
    Date jobStarted;
    Date jobReady;
    Date jobRetrieved;
}
