package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.conf.Factories;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Console.println("\nList of mechanics \n");

        List<MechanicDto> mechanics = Factories.service.forMechanicCrudService().findAll();
        if(mechanics.isEmpty()) {
        	Console.print("No mechanics found");
        } else {
        	for (MechanicDto m: mechanics) {
        		Printer.printMechanic(m);
        	}
        }
    }
}