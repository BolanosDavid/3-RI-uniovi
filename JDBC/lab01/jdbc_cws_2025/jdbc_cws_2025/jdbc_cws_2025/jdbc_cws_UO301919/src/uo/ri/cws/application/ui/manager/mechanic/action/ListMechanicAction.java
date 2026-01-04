package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
    	
        // Get info
        String nif = Console.readString("nif");

        Console.println("\nMechanic information \n");
        
        //MechanicCrudService service = new MechanicCrudServiceImpl();
        //Optional<MechanicDto> optional = service.findById(nif);
        
        Optional<MechanicDto> optional = Factories.service.forMechanicCrudService().findByNif(nif);
        
        if(optional.isEmpty()) {
        	Console.print("There is not mechanic with that nif");
        } else {
        	MechanicDto m = optional.get();
        	Printer.printMechanic(m);
        }
        
    }
}