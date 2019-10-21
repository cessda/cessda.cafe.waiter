package org.cessda.cafe.waiter.service;

/*
 * Java Engine class to process logic on /configure end point 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cessda.cafe.waiter.data.model.Machines;
import org.cessda.cafe.waiter.database.DatabaseClass;

public class MachineService {
	
	private Map<String, Machines> machines = DatabaseClass.getMachines();

// MachineService class construct method	
	public MachineService() {
//	 machines.put("machine", new Machines(null, "http://localhost:1337", "http://localhost:2222" ));	
	}

// Returns configured coffee and cashier configurations
	public List<Machines> getMachines(){
		
		return new ArrayList<Machines>(machines.values());
	}
	
// Post configurations of coffee and cashier machines
	public Machines postMachines(Machines machine){

		machines.put(machine.getMachineId(), machine);
  
		return machine;
	}
	
// Remove configured coffee and cashier configurations	
	public Machines deleteMachines(){
	    return   machines.remove(null);   			 
		}

	
// TO BE DONE	//
	public Machines deleteCashier() {
		return null;
	}
	
	
// TO BE DONE	//	
	public Machines deleteCoffee() {
		return null;
	}
	

}
