package eu.cessda.cafe.waiter.data.model;

import java.util.Arrays;

public class Order {
	
	
	private String orderId;
	private String orderPlaced;
	private int ordersize;
	private Product[] coffees;
	private String orderDelivered;
	
	public Order() {
		super();
	}
	
	public Order(String orderId) {
		super();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderPlaced() {
		return orderPlaced;
	}
	public void setOrderPlaced(String orderPlaced) {
		this.orderPlaced = orderPlaced;
	}
	public int getOrdersize() {
		return ordersize;
	}
	public void setOrdersize(int ordersize) {
		this.ordersize = ordersize;
	}
	public Product[] getCoffees() {
		return coffees;
	}
	public void setCoffees(Product[] coffees) {
		this.coffees = coffees;
	}
	public String getOrderDelivered() {
		return orderDelivered;
	}
	public void setOrderDelivered(String now) {
		this.orderDelivered = now;
	}

	public Order(String orderId, String orderPlaced, int ordersize, Product[] coffees, String orderDelivered) {
		super();
		this.orderId = orderId;
		this.orderPlaced = orderPlaced;
		this.ordersize = ordersize;
		this.coffees = coffees;
		this.orderDelivered = orderDelivered;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderPlaced=" + orderPlaced + ", ordersize=" + ordersize + ", coffees="
				+ Arrays.toString(coffees) + ", orderDelivered=" + orderDelivered + "]";
	}
	
	

}
