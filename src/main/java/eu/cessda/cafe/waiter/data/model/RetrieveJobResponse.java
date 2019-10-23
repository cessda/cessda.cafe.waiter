package eu.cessda.cafe.waiter.data.model;

import java.util.Arrays;

public class RetrieveJobResponse {
	private String jobId;
	private Product[] product;
	private String jobStarted;
	private String jobReady;
	private String jobRetrieved;
	
	
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
	public String getJobStarted() {
		return jobStarted;
	}
	public void setJobStarted(String jobStarted) {
		this.jobStarted = jobStarted;
	}
	public String getJobReady() {
		return jobReady;
	}
	public void setJobReady(String jobReady) {
		this.jobReady = jobReady;
	}
	public String getJobRetrieved() {
		return jobRetrieved;
	}
	public void setJobRetrieved(String jobRetrieved) {
		this.jobRetrieved = jobRetrieved;
	}
	
	public RetrieveJobResponse(String jobId, Product[] product, String jobStarted, String jobReady,
			String jobRetrieved) {
		super();
		this.jobId = jobId;
		this.product = product;
		this.jobStarted = jobStarted;
		this.jobReady = jobReady;
		this.jobRetrieved = jobRetrieved;
	}
	
	@Override
	public String toString() {
		return "RetrieveJobResponse [jobId=" + jobId + ", product=" + Arrays.toString(product) + ", jobStarted="
				+ jobStarted + ", jobReady=" + jobReady + ", jobRetrieved=" + jobRetrieved + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
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
		RetrieveJobResponse other = (RetrieveJobResponse) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}
	
	
	
	

}
