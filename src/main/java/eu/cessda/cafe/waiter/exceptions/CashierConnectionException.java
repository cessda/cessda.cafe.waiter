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

package eu.cessda.cafe.waiter.exceptions;

import java.io.IOException;
import java.net.URI;

/**
 * Represents connection errors to the cashier
 */
public class CashierConnectionException extends Exception {
    private CashierConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a CashierConnectionException with a predefined message.
     *
     * @param cashierUrl The cashier url.
     * @param e          The inner IOException.
     * @return The generated exception.
     */
    public static CashierConnectionException exceptionMessage(URI cashierUrl, IOException e) {
        return new CashierConnectionException("Error connecting to cashier " + cashierUrl, e);
    }
}
