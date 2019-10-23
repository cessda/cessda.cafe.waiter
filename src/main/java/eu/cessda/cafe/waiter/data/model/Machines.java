package eu.cessda.cafe.waiter.data.model;

/*
 * Java class to keep coffee machine and cashier end points configurations  
 */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Machines {
	private String machineId;
	private String coffeeMachine;
	private String cashier;
	


	public Machines(String machineId, String cashier, String coffeeMachine) {
		this.machineId = machineId;
		this.cashier = cashier;
		this.coffeeMachine = coffeeMachine;
		
	}
		
	public Machines() {

	}
		
	public String getMachineId() {
		return machineId;
	}


	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}


	public String getCoffeeMachine() {
		return coffeeMachine;
	}
	public void setCoffeeMachine(String coffeeMachine) {
		this.coffeeMachine = coffeeMachine;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
    
}
