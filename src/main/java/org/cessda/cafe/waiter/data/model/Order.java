package org.cessda.cafe.waiter.data.model;

/*
 * Java class to keep the hold data for response for /order-retrieve path 
 */
import java.security.Timestamp;
import java.util.UUID;

public class Order {
	
	
	public UUID orderId;
	public Timestamp orderPlaced; 
	public int ordersize; 
	public Coffees coffees; 
	public Timestamp orderDelivered;
	
	public Order() {
		super();
	}
	
	public Order(UUID orderId) {
		super();
		this.orderId = orderId;
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
	public int getOrdersize() {
		return ordersize;
	}
	public void setOrdersize(int ordersize) {
		this.ordersize = ordersize;
	}
	public Coffees getCoffees() {
		return coffees;
	}
	public void setCoffees(Coffees coffees) {
		this.coffees = coffees;
	}
	public Timestamp getOrderDelivered() {
		return orderDelivered;
	}
	public void setOrderDelivered(Timestamp orderDelivered) {
		this.orderDelivered = orderDelivered;
	}
	
	
	

}
