/*
 * Copyright CESSDA ERIC 2025.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package eu.cessda.cafe.waiter.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Java Class to store jobs collected from Cashier or Coffee Machine
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Job {
    @Id private UUID jobId;
    private Product product;
    private UUID orderId;
    private OffsetDateTime orderPlaced;
    private OffsetDateTime orderDelivered;
    private int orderSize;
    private URI machine;
    private OffsetDateTime jobStarted;
    private OffsetDateTime jobRetrieved;

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OffsetDateTime getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(OffsetDateTime orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public OffsetDateTime getOrderDelivered() {
        return orderDelivered;
    }

    public void setOrderDelivered(OffsetDateTime orderDelivered) {
        this.orderDelivered = orderDelivered;
    }

    public int getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(int orderSize) {
        this.orderSize = orderSize;
    }

    public URI getMachine() {
        return machine;
    }

    public void setMachine(URI machine) {
        this.machine = machine;
    }

    public OffsetDateTime getJobStarted() {
        return jobStarted;
    }

    public void setJobStarted(OffsetDateTime jobStarted) {
        this.jobStarted = jobStarted;
    }

    public OffsetDateTime getJobRetrieved() {
        return jobRetrieved;
    }

    public void setJobRetrieved(OffsetDateTime jobRetrieved) {
        this.jobRetrieved = jobRetrieved;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", product=" + product +
                ", orderId=" + orderId +
                ", orderPlaced=" + orderPlaced +
                ", orderDelivered=" + orderDelivered +
                ", orderSize=" + orderSize +
                ", machine=" + machine +
                ", jobStarted=" + jobStarted +
                ", jobRetrieved=" + jobRetrieved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return orderSize == job.orderSize && Objects.equals(jobId, job.jobId) && product == job.product && Objects.equals(orderId, job.orderId) && Objects.equals(orderPlaced, job.orderPlaced) && Objects.equals(orderDelivered, job.orderDelivered) && Objects.equals(machine, job.machine) && Objects.equals(jobStarted, job.jobStarted) && Objects.equals(jobRetrieved, job.jobRetrieved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, product, orderId, orderPlaced, orderDelivered, orderSize, machine, jobStarted, jobRetrieved);
    }
}
