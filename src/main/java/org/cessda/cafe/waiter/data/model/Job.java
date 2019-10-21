package org.cessda.cafe.waiter.data.model;

/*
 * Java Class to store jobs collected from Cashier or Coffee Machine 
 */

import java.security.Timestamp;
import java.util.UUID;

public class Job {
	UUID jobId;
	Timestamp jobStarted; 
	UUID orderId; 
	Timestamp orderPlaced;
	int orderSize;
	Machines machine;
	Product product;
	Timestamp orderDelivered;
	Coffees coffees; 
	
	
	
	public Job() {
		super();
	}

	
	public Coffees getCoffees() {
		return coffees;
	}

	public void setCoffees(Coffees coffees) {
		this.coffees = coffees;
	}

	public Job(UUID orderId) {
		super();
		this.orderId = orderId;
	}

	public UUID getJobId() {
		return jobId;
	}
	public Timestamp getOrderDelivered() {
		return orderDelivered;
	}

	public void setOrderDelivered(Timestamp orderDelivered) {
		this.orderDelivered = orderDelivered;
	}

	public void setJobId(UUID jobId) {
		this.jobId = jobId;
	}

	public Timestamp getJobStarted() {
		return jobStarted;
	}

	public void setJobStarted(Timestamp jobStarted) {
		this.jobStarted = jobStarted;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	

}
