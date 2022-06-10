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

package eu.cessda.cafe.waiter;

/*
 * Setup the Application path for the program.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.cessda.cafe.waiter.database.Database;
import eu.cessda.cafe.waiter.helpers.CoffeeMachineHelper;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import jakarta.ws.rs.ApplicationPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.Duration;

@ApplicationPath("/")
public class WaiterApplication extends ResourceConfig {
    private static final Logger log = LogManager.getLogger(WaiterApplication.class);

    private static final URI DEFAULT_CASHIER_URL = URI.create("http://localhost:1336/");
    private static URI cashierUrl;

    public WaiterApplication() {
        packages("eu.cessda.cafe.waiter");

        // Read cashier URL
        initCashierUrl();

        // Initialise dependency injection
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                // Internal dependencies
                bindAsContract(CoffeeMachineHelper.class);
                bind(new Database()).to(Database.class);
                bindAsContract(JobService.class);
                bindAsContract(OrderService.class);

                // HTTP Client and object mapper
                bind(HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .followRedirects(HttpClient.Redirect.NORMAL).build()
                ).to(HttpClient.class);
                bind(getObjectMapper()).to(ObjectMapper.class);
            }

            private ObjectMapper getObjectMapper() {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                return mapper;
            }
        });
    }

    private static void initCashierUrl() {
        String cashierUrl = System.getenv("CASHIER_URL");
        try {
            if (cashierUrl != null && !cashierUrl.isEmpty()) {
                WaiterApplication.cashierUrl = new URL(cashierUrl).toURI();
                log.info("Using cashier {}", cashierUrl);
                return;
            }
        } catch (MalformedURLException | URISyntaxException e) {
            log.warn("{} is not a valid URL, not configuring", cashierUrl);
        }

        // This makes sure a cashier URL is always configured
        log.info("Using default cashier {}", DEFAULT_CASHIER_URL);
        WaiterApplication.cashierUrl = DEFAULT_CASHIER_URL;
    }

    public static URI getCashierUrl() {
        return cashierUrl;
    }
}
