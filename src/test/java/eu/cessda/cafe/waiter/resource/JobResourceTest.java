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

package eu.cessda.cafe.waiter.resource;

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.exceptions.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

public class JobResourceTest {

    private static final String MOCKED = "Mocked!";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private JobService jobService;
    private JobResource jobResource;

    public JobResourceTest() {
        jobService = Mockito.mock(JobService.class);
        jobResource = new JobResource(jobService);
    }

    @Test
    public void shouldReturnApiMessageOnSuccess() throws CashierConnectionException {
        // Setup
        Mockito.doReturn(new ApiMessage(MOCKED)).when(jobService).collectJobs();

        // Act
        var response = jobResource.postCollectJobs();

        Assert.assertEquals(ApiMessage.class, response.getEntity().getClass());
        ApiMessage message = (ApiMessage) response.getEntity();
        Assert.assertEquals(MOCKED, message.getMessage());
    }

    @Test
    public void shouldReturnServerErrorOnCasherConnectionError() throws CashierConnectionException {
        // Setup
        Mockito.doThrow(new CashierConnectionException(URI.create("http://localhost:1336/"),
                new IOException(MOCKED))).when(jobService).collectJobs();

        // Act
        var response = jobResource.postCollectJobs();

        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}