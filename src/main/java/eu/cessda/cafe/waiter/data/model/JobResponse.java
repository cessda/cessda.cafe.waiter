package eu.cessda.cafe.waiter.data.model;

import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

/*
 * Java Class to store data from http://cashier/processed-jobs
 */
@Data
public class JobResponse {
    private UUID jobId;
	private String product;
    private UUID orderId;
    private Date orderPlaced;
	private int orderSize;
    private URL machine;
	private String jobStarted;
}
