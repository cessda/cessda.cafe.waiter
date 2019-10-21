package org.cessda.cafe.waiter.data.model;

/*
 * Java class to store data for /order-history from the cashier 
 */

import java.security.Timestamp;
import java.util.UUID;

public class OrderHistory {
	UUID jobId;
	Product product[];
	UUID orderId; 
	Timestamp orderPlaced;
	Timestamp orderDelivered;
	int orderSize;
	Machines machine;
	Timestamp jobstarted; 
	Timestamp jobRetrieved;
	
	
	
	public OrderHistory() {
		
	}
	
	public UUID getJobId() {
		return jobId;
	}
	public void setJobId(UUID jobId) {
		this.jobId = jobId;
	}
	public Product[] getProduct() {
		return product;
	}
	public void setProduct(Product[] product) {
		this.product = product;
	}
	public UUID getOrderId() {
		return orderId;
	}
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	public Timestamp getOrderPlaced() {
		return orderPlaced;
	}
	public void setOrderPlaced(Timestamp orderPlaced) {
		this.orderPlaced = orderPlaced;
	}
	public Timestamp getOrderDelivered() {
		return orderDelivered;
	}
	public void setOrderDelivered(Timestamp orderDelivered) {
		this.orderDelivered = orderDelivered;
	}
	public int getOrderSize() {
		return orderSize;
	}
	public void setOrderSize(int orderSize) {
		this.orderSize = orderSize;
	}
	public Machines getMachine() {
		return machine;
	}
	public void setMachine(Machines machine) {
		this.machine = machine;
	}
	public Timestamp getJobstarted() {
		return jobstarted;
	}
	public void setJobstarted(Timestamp jobstarted) {
		this.jobstarted = jobstarted;
	}
	public Timestamp getJobRetrieved() {
		return jobRetrieved;
	}
	public void setJobRetrieved(Timestamp jobRetrieved) {
		this.jobRetrieved = jobRetrieved;
	}
	
	
    
}
