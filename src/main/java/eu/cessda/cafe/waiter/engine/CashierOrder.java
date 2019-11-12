package eu.cessda.cafe.waiter.engine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.cessda.cafe.waiter.data.response.OrderHistoryResponse;

public class CashierOrder {
	
	private final URL orderHistoryEndpoint;
    

    public CashierOrder(URL cashierUrl) {
        try {
            orderHistoryEndpoint = new URL(cashierUrl, "order-history");
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    
    public List<OrderHistoryResponse> getOrderHistory() throws IOException {
        return new ObjectMapper().readValue(orderHistoryEndpoint, new TypeReference<List<OrderHistoryResponse>>() {
        });
    }

}
