package eu.cessda.cafe.waiter.message;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequestListener {
	
	public void requestInitialized (String requestId) {
	//	log.debug("-----Request ID Initialised-----");
		if (requestId != null) {
		ThreadContext.put("X-Request-Id",requestId);;
	//	log.info("This is Request-ID from client {}", requestId);
		}else {
			ThreadContext.push("X-Request-Id", UUID.randomUUID());;
		}
	}
	
	public void requestDestroyed() {
	//    log.debug("-----Request ID Destroyed-----");
	    ThreadContext.clearAll();
	 }
}
