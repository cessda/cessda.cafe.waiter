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

package eu.cessda.cafe.waiter.service;

import java.net.URI;

/**
 * Represents connection errors to the cashier
 */
public class CashierConnectionException extends Exception {

    private final URI cashierUrl;

    /**
     * Create a CashierConnectionException with a predefined message.
     *
     * @param cashierUrl The cashier url.
     * @param cause      The cause.
     */
    public CashierConnectionException(URI cashierUrl, Throwable cause) {
        super("Error connecting to cashier " + cashierUrl + ": " + cause.toString(), cause);
        this.cashierUrl = cashierUrl;
    }

    public URI getCashierUrl() {
        return cashierUrl;
    }
}
