package eu.cessda.cafe.waiter.message;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RequestListerner {
	
	public void requestInitialized () {
		log.debug("-----Request ID Initialized-----");
		ThreadContext.push("RequestId",UUID.randomUUID().toString());;
	}
	
	public void requestDestroyed() {
	    log.debug("-----Request ID Destroyed-----");
	    ThreadContext.clearAll();
	 }
}
