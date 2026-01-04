package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindMechanicWithContractInForceAction implements Action{

	@Override
	public void execute() throws Exception {
		Console.print("\nList of mechanics with contract in forcce\n");
		
		List<MechanicDto> mechanics;
		mechanics = Factories.service.forMechanicCrudService().findMechanicsInForce();
		
		if(mechanics.isEmpty()) {
        	Console.print("No mechanics with contract in force found");
        } else {
        	for (MechanicDto m: mechanics) {
        		Printer.printMechanic(m);
        	}
        }
		
	}

}
