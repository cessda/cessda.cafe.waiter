package eu.cessda.cafe.waiter.data.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Java Class to store jobs collected from Cashier or Coffee Machine
 */
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
