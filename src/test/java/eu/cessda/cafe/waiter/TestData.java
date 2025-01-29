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

package eu.cessda.cafe.waiter;

import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.model.Product;
import eu.cessda.cafe.waiter.database.JobRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;

public class TestData implements JobRepository {
    // Record the current time for reference
    OffsetDateTime currentTime = OffsetDateTime.now();

    // A specific order
    UUID testOrderId = UUID.randomUUID();
    int testOrderSize = 1;

    Map<UUID, Job> jobs = new HashMap<>();

    public TestData(UUID orderId, UUID jobId) {
        var testJob = new Job();
        testJob.setJobId(UUID.randomUUID());
        testJob.setProduct(Product.CAPPUCCINO);
        testJob.setOrderId(testOrderId);
        testJob.setOrderPlaced(currentTime.minusMinutes(2));
        testJob.setOrderSize(testOrderSize);
        testJob.setMachine(URI.create("http://localhost:1337/"));
        testJob.setJobStarted(currentTime.minusMinutes(1));

        var job = new Job();
        job.setJobId(jobId);
        job.setOrderId(orderId);
        job.setProduct(Product.COFFEE);
        job.setOrderPlaced(OffsetDateTime.now().minusHours(1));
        job.setMachine(URI.create("http://localhost:1336"));
        job.setJobStarted(OffsetDateTime.now().minusMinutes(16));

        jobs.put(testJob.getJobId(), testJob);
        jobs.put(job.getJobId(), job);
    }

    @Override
    public List<Job> findAllByOrderId(UUID orderId) {
        return jobs.values().stream().filter(job -> job.getOrderId().equals(orderId)).toList();
    }

    @Override
    public <S extends Job> S save(S entity) {
        jobs.put(entity.getJobId(), entity);
        return entity;
    }

    @Override
    public <S extends Job> List<S> saveAll(Iterable<S> entities) {
        var entitiesList = new ArrayList<S>();
        for (var entity : entities) {
            jobs.put(entity.getJobId(), entity);
            entitiesList.add(entity);
        }
        return entitiesList;
    }

    @Override
    public Optional<Job> findById(UUID uuid) {
        return Optional.ofNullable(jobs.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return jobs.containsKey(uuid);
    }

    @Override
    public List<Job> findAll() {
        return List.copyOf(jobs.values());
    }

    @Override
    public List<Job> findAllById(Iterable<UUID> uuids) {
        var entitiesList = new ArrayList<Job>();
        for (var id : uuids) {
            var job = jobs.get(id);
            entitiesList.add(job);
        }
        return entitiesList;
    }

    @Override
    public long count() {
        return jobs.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        jobs.remove(uuid);
    }

    @Override
    public void delete(Job entity) {
        jobs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(entity))
                .map(Map.Entry::getKey)
                .findAny()
                .ifPresent(jobs::remove);
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for (var id : uuids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Job> entities) {
        for (var entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        jobs.clear();
    }
}
