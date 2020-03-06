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

package eu.cessda.cafe.waiter;

/*
 * Setup the Application path for the program.
 */

import com.google.common.base.Strings;
import eu.cessda.cafe.waiter.database.Database;
import eu.cessda.cafe.waiter.helpers.CashierHelper;
import eu.cessda.cafe.waiter.helpers.CoffeeMachineHelper;
import eu.cessda.cafe.waiter.service.JobService;
import eu.cessda.cafe.waiter.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Log4j2
@ApplicationPath("/")
public class WaiterApplication extends ResourceConfig {
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
                bindAsContract(CashierHelper.class);
                bindAsContract(CoffeeMachineHelper.class);
                bindAsContract(Database.class);
                bindAsContract(JobService.class);
                bindAsContract(OrderService.class);
            }
        });
    }

    private static void initCashierUrl() {
        String cashierUrl = System.getenv("CASHIER_URL");
        boolean isUrl = false;
        try {
            if (!Strings.isNullOrEmpty(cashierUrl)) {
                WaiterApplication.cashierUrl = new URL(cashierUrl).toURI();
                isUrl = true;
                log.info("Using cashier {}", cashierUrl);
            }

        } catch (MalformedURLException | URISyntaxException e) {
            log.warn("{} is not a valid URL, not configuring", cashierUrl);
        }
        if (!isUrl) {
            log.info("Using default cashier {}", DEFAULT_CASHIER_URL);
            WaiterApplication.cashierUrl = DEFAULT_CASHIER_URL;
        }
    }

    public static URI getCashierUrl() {
        return cashierUrl;
    }
}
