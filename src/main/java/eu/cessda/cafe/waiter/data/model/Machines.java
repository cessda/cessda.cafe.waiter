package eu.cessda.cafe.waiter.data.model;

/*
 * Java class to keep coffee machine and cashier end points configurations  
 */

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
public class Machines {
	private String machineId;
	private String coffeeMachine;
	private String cashier;
}
