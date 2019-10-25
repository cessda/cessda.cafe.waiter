package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.data.model.Order;
import eu.cessda.cafe.waiter.data.model.Product;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import lombok.extern.log4j.Log4j2;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Java Engine class to process logic on /retrieve-order/ end points
 */

@Log4j2
public class OrderService {

    private final Map<String, Order> orderList = DatabaseClass.getOrder();
    Order order = new Order();
    private Product product1 = Product.CAPPUCCINO;
    private Product product2 = Product.COFFEE;
    private Product product3 = Product.KAKAO;


    // OrderService class construct
    public OrderService() {
        orderList.put("00000000-FFFF-FFFF-FFFF-000000000000", new Order("00000000-FFFF-FFFF-FFFF-000000000000", "2019-07-31T01:00:00.000Z", 2, new Product[]{product1, product2}, "2019-07-31T01:00:01.000Z"));
        orderList.put("00000000-AAAA-AAAA-AAAA-000000000000", new Order("00000000-AAAA-AAAA-AAAA-000000000000", "2019-07-31T01:00:00.000Z", 2, new Product[]{product2, product3}, ""));
        orderList.put("00000000-BBBB-BBBB-BBBB-000000000000", new Order("00000000-BBBB-BBBB-BBBB-000000000000", "2019-07-31T01:00:00.000Z", 3, new Product[]{product1, product3}, ""));
    }


    // Returns all orders from from cashier
    public List<Order> getOrder() {
        return new ArrayList<>(orderList.values());
    }


    public Order getSpecificOrder(String orderId) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T01:'HH:mm:ss'Z'");
        Date date = new Date(System.currentTimeMillis());
        String now = formatter.format(date);

        // update Order delivery date (time)
        orderList.get(orderId).setOrderDelivered(now);

        log.debug("Order {} Delivery updated", orderId);

        return orderList.get(orderId);

    }
}
	
	   



	


