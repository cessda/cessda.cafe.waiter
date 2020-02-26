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

package eu.cessda.cafe.waiter.message;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

@Log4j2
public class RequestListener {
	
	public void requestInitialized (String requestId) {
		if (requestId != null) {
			ThreadContext.put("X-Request-Id", requestId);
		}else {
			ThreadContext.push("X-Request-Id", UUID.randomUUID());
		}
	}
	
	public void requestDestroyed() {
	    ThreadContext.clearAll();
	 }
}
