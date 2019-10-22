package eu.cessda.cafe.waiter.data.model;

public class Order {
	
	
	private String orderId;
	private String orderPlaced;
	private int ordersize;
	private Coffees[] coffees;
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
	public Coffees[] getCoffees() {
		return coffees;
	}
	public void setCoffees(Coffees[] coffees) {
		this.coffees = coffees;
	}
	public String getOrderDelivered() {
		return orderDelivered;
	}
	public void setOrderDelivered(String now) {
		this.orderDelivered = now;
	}
	
	

}
