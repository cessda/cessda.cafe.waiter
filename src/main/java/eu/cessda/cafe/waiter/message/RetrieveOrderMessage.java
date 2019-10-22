package eu.cessda.cafe.waiter.message;

public class RetrieveOrderMessage {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RetrieveOrderMessage(String message) {
		super();
		this.message = message;
	}

	public RetrieveOrderMessage() {
		super();
	}
	
	

}
