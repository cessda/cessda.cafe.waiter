package eu.cessda.cafe.waiter.data.model;

/*
 * Java Class to store data from http://cashier/processed-jobs
 */
public class JobResponse {

	
	private String  jobId;
	private String product;
	private String orderId;
	private String orderPlaced;
	private int orderSize;
	private String machine;
	private String jobStarted;
	
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
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
	public String getJobStarted() {
		return jobStarted;
	}
	public void setJobStarted(String jobStarted) {
		this.jobStarted = jobStarted;
	}
	public JobResponse(String jobId, String product, String orderId, String orderPlaced, int orderSize, String machine,
			String jobStarted) {
		super();
		this.jobId = jobId;
		this.product = product;
		this.orderId = orderId;
		this.orderPlaced = orderPlaced;
		this.orderSize = orderSize;
		this.machine = machine;
		this.jobStarted = jobStarted;
	}
	public JobResponse() {
		super();
	}
	@Override
	public String toString() {
		return "JobResponse [jobId=" + jobId + ", product=" + product + ", orderId=" + orderId + ", orderPlaced="
				+ orderPlaced + ", orderSize=" + orderSize + ", machine=" + machine + ", jobStarted=" + jobStarted
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((jobStarted == null) ? 0 : jobStarted.hashCode());
		result = prime * result + ((machine == null) ? 0 : machine.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderPlaced == null) ? 0 : orderPlaced.hashCode());
		result = prime * result + orderSize;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobResponse other = (JobResponse) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (jobStarted == null) {
			if (other.jobStarted != null)
				return false;
		} else if (!jobStarted.equals(other.jobStarted))
			return false;
		if (machine == null) {
			if (other.machine != null)
				return false;
		} else if (!machine.equals(other.machine))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderPlaced == null) {
			if (other.orderPlaced != null)
				return false;
		} else if (!orderPlaced.equals(other.orderPlaced))
			return false;
		if (orderSize != other.orderSize)
			return false;
		if (product == null) {
			return other.product == null;
		} else return product.equals(other.product);
	}
	
	
	


}
