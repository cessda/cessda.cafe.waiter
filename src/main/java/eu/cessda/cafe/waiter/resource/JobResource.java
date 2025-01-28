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

/*
 * Java Resource class to expose /collect-jobs end point.
 */

import eu.cessda.cafe.waiter.data.model.ApiMessage;
import eu.cessda.cafe.waiter.service.CashierConnectionException;
import eu.cessda.cafe.waiter.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect-jobs")
public class JobResource {

    private final JobService jobService;

    @Autowired
    public JobResource(JobService jobService) {
        this.jobService = jobService;
    }

    // Return message from a post method on /collect-jobs
    @PostMapping
    public ApiMessage postCollectJobs() throws CashierConnectionException {
        return jobService.collectJobs();
    }
}
