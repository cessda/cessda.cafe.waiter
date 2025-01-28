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


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="ORDR") // ORDER is an SQL keyword and cannot be used as a table name
public class Order {
    @Id private UUID orderId;
    private OffsetDateTime orderPlaced;
    private int orderSize;
    @OneToMany private Set<Job> jobs;
    private OffsetDateTime orderDelivered;

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

    public int getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(int orderSize) {
        this.orderSize = orderSize;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public OffsetDateTime getOrderDelivered() {
        return orderDelivered;
    }

    public void setOrderDelivered(OffsetDateTime orderDelivered) {
        this.orderDelivered = orderDelivered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderPlaced=" + orderPlaced +
                ", orderSize=" + orderSize +
                ", jobs=" + jobs +
                ", orderDelivered=" + orderDelivered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderSize == order.orderSize && Objects.equals(orderId, order.orderId) && Objects.equals(orderPlaced, order.orderPlaced) && Objects.equals(jobs, order.jobs) && Objects.equals(orderDelivered, order.orderDelivered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderPlaced, orderSize, jobs, orderDelivered);
    }
}
