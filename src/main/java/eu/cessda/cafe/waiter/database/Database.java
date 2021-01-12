/*
 * Copyright CESSDA ERIC 2020.
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

package eu.cessda.cafe.waiter.database;

/*
 * HashMap to collect and hold data in Java Memory
 */

import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.data.model.Order;
import org.jvnet.hk2.annotations.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class Database {
    private final ConcurrentHashMap<UUID, Order> order = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Job> job = new ConcurrentHashMap<>();

    public ConcurrentMap<UUID, Order> getOrder() {
        return order;
    }

    public ConcurrentMap<UUID, Job> getJob() {
        return job;
    }
}
