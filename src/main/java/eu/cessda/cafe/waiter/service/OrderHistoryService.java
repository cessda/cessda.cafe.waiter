/*
 * Copyright CESSDA ERIC 2019.
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

package eu.cessda.cafe.waiter.service;

import eu.cessda.cafe.waiter.data.model.Job;
import eu.cessda.cafe.waiter.database.DatabaseClass;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.UUID;

@Log4j2
public class OrderHistoryService {

    public Collection<Job> getOrderHistory() {
        return DatabaseClass.job.values();
    }

    public Job getOrderHistory(UUID orderId) {
        return DatabaseClass.job.get(orderId);
    }
}
	
	

    
	


