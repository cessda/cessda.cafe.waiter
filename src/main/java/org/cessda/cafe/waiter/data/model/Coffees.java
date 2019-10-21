package org.cessda.cafe.waiter.data.model;

/*
 * Java class to hold array of coffee product 
 */
public class Coffees {
	Product product[];
	
	public Coffees(Product[] product) {
		super();
		this.product = product;
		
	}
	public Product[] getProduct() {
		return product;
	}

	public void setProduct(Product[] product) {
		this.product = product;
	}

}
