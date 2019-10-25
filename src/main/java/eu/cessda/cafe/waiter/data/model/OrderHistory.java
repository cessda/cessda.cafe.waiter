package eu.cessda.cafe.waiter.data.model;

/*
 * Java class to store data for /order-history from the cashier 
 */

import lombok.Data;

@Data
public class OrderHistory {
	private String jobId;
	private Product[] product;
	private String orderId;
	private String orderPlaced;
	private String orderDelivered;
	private int orderSize;
	private String machine;
	private String jobStarted;
	private String jobRetrieved;
}

