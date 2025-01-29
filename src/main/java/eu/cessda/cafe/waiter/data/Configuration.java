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

package eu.cessda.cafe.waiter.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties
public record Configuration(
    URI cashierUrl
) {
    private static final URI DEFAULT_CASHIER_URL = URI.create("http://localhost:1336/");

    private static final Logger log = LogManager.getLogger(Configuration.class);

    public Configuration {
        if (cashierUrl != null) {
            if (cashierUrl.getScheme().startsWith("http")) {
                log.info("Using cashier {}", cashierUrl);
            } else {
                throw new IllegalStateException("Invalid scheme \"" + cashierUrl.getScheme() + "\" in cashier URI \"" + cashierUrl + "\"");
            }
        } else {
            // This makes sure a cashier URL is always configured
            log.info("Using default cashier {}", DEFAULT_CASHIER_URL);
            cashierUrl = DEFAULT_CASHIER_URL;
        }
    }
}
