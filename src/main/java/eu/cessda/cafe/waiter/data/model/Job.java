package eu.cessda.cafe.waiter.data.model;

/*
 * Java Class to store jobs collected from Cashier or Coffee Machine 
 */

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Job {
	private UUID jobId;
    private Date jobStarted;
	private UUID orderId;
    private Date orderPlaced;
	private int orderSize;
	private Machines machine;
	private Product product;
    private Date orderDelivered;
	private Coffees coffees;
}
