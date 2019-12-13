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

package eu.cessda.cafe.waiter.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Java Class to store jobs collected from Cashier or Coffee Machine
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    private UUID jobId;
    private Product product;
    private UUID orderId;
    private OffsetDateTime orderPlaced;
    private OffsetDateTime orderDelivered;
    private int orderSize;
    private URI machine;
    private OffsetDateTime jobStarted;
    private OffsetDateTime jobRetrieved;
}
