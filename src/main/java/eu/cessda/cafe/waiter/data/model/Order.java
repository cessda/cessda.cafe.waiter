package eu.cessda.cafe.waiter.data.model;

import lombok.Data;

@Data
public class Order {
	private String orderId;
	private String orderPlaced;
	private int orderSize;
	private Product[] coffees;
	private String orderDelivered;
}
