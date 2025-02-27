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

package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.service.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class JobResourceTest {

    private static final String MOCKED = "Mocked!";

    @Test
    void shouldReturnApiMessageOnSuccess() throws CashierConnectionException {
        // Setup
        var jobService = Mockito.mock(JobService.class);
        var jobResource = new JobResource(jobService);
        Mockito.doReturn(new ApiMessage(MOCKED)).when(jobService).collectJobs();

        // Act
        var response = jobResource.postCollectJobs();
        assertNotNull(response);
        assertEquals(MOCKED, response.message());
    }

    @Test
    void shouldReturnServerErrorOnCashierConnectionError() throws CashierConnectionException {
        // Setup
        var jobService = Mockito.mock(JobService.class);
        var jobResource = new JobResource(jobService);
        Mockito.doThrow(
                new CashierConnectionException(URI.create("http://localhost:1336/"), new IOException(MOCKED))
        ).when(jobService).collectJobs();

        // Act
        assertThrows(CashierConnectionException.class, jobResource::postCollectJobs);
    }
}