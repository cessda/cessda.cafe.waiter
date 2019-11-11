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

package eu.cessda.cafe.waiter.service;

/*
 * Java Engine class to process logic on /configure end point
 */

import eu.cessda.cafe.waiter.data.model.Machines;
import eu.cessda.cafe.waiter.database.DatabaseClass;

import java.net.URL;
import java.util.Collection;

public class MachineService {

    // MachineService class construct method
    public MachineService() {
//	 machines.put("machine", new Machines(null, "http://localhost:1337", "http://localhost:2222" ));	
    }

    // Returns configured coffee and cashier configurations
    public Collection<Machines> getMachines() {
        return DatabaseClass.machine.values();
    }

    // Post configurations of coffee and cashier machines
    public Machines postMachines(URL cashierUrl) {
        var machine = new Machines();
        machine.setCashier(cashierUrl);

        DatabaseClass.machine.put("cashier", machine);

        return machine;
    }

    // Remove configured coffee and cashier configurations
    public Machines deleteMachines(URL machine) {
        return DatabaseClass.machine.remove(machine);
    }

}
