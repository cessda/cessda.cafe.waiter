/*
 * Copyright CESSDA ERIC 2022.
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

package eu.cessda.cafe.waiter.data.response;

import eu.cessda.cafe.waiter.data.model.Product;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CoffeeMachineResponse(
        UUID jobId,
        Product product,
        OffsetDateTime jobStarted,
        OffsetDateTime jobReady,
        OffsetDateTime jobRetrieved
) {
}
