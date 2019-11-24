package eu.cessda.cafe.waiter.message;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequestListener {
	
	public void requestInitialized (String requestId) {
		log.debug("-----Request ID Initialized-----");
		if (requestId != null) {
		ThreadContext.put("X-Request-ID",requestId);;
		}else {
			ThreadContext.push("X-Request-ID", UUID.randomUUID());;
		}
	}
	
	public void requestDestroyed() {
	    log.debug("-----Request ID Destroyed-----");
	    ThreadContext.clearAll();
	 }
}
