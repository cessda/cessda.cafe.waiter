package eu.cessda.cafe.waiter.data.model;

/*
 * Java class to store data for /order-history from the cashier 
 */

import java.util.Arrays;

public class OrderHistory {
	private String jobId;
	private Product[] product;
	private String orderId;
	private String orderPlaced;
	private String orderDelivered;
	private int orderSize;
	private String machine;
	private String jobstarted;
	private String jobRetrieved;
	
	
	
	public OrderHistory() {
		// No implementation yet
		throw new UnsupportedOperationException();
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Product[] getProduct() {
		return product;
	}
	public void setProduct(Product[] product) {
		this.product = product;
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
	public String getOrderDelivered() {
		return orderDelivered;
	}
	public void setOrderDelivered(String orderDelivered) {
		this.orderDelivered = orderDelivered;
	}
	public int getOrderSize() {
		return orderSize;
	}
	public void setOrderSize(int orderSize) {
		this.orderSize = orderSize;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getJobstarted() {
		return jobstarted;
	}
	public void setJobstarted(String jobstarted) {
		this.jobstarted = jobstarted;
	}
	public String getJobRetrieved() {
		return jobRetrieved;
	}
	public void setJobRetrieved(String jobRetrieved) {
		this.jobRetrieved = jobRetrieved;
	}
	
	

	public OrderHistory(String orderId, String jobId, Product[] product, String orderPlaced, String orderDelivered,
			int orderSize, String machine, String jobstarted, String jobRetrieved) {
		super();
		this.orderId = orderId;
		this.jobId = jobId;
		this.product = product;
		this.orderPlaced = orderPlaced;
		this.orderDelivered = orderDelivered;
		this.orderSize = orderSize;
		this.machine = machine;
		this.jobstarted = jobstarted;
		this.jobRetrieved = jobRetrieved;
	}

	@Override
	public String toString() {
		return "OrderHistory [orderId=" + orderId + ", jobId=" + jobId + ", product=" + Arrays.toString(product)
				+ ", orderPlaced=" + orderPlaced + ", orderDelivered=" + orderDelivered + ", orderSize=" + orderSize
				+ ", machine=" + machine + ", jobstarted=" + jobstarted + ", jobRetrieved=" + jobRetrieved + "]";
	}

	
}
