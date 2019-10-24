package eu.cessda.cafe.waiter.data.model;

import lombok.Data;

@Data
public class ProcessedJobs {
    private String jobId;
    private String product;
    private String orderId;
    private String orderPlaced;
    private int orderSize;
    private String machine;
    private String jobStarted;
}
