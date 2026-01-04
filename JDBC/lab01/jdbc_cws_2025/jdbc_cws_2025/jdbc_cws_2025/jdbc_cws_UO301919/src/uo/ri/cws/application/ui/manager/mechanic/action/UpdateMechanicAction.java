package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {
	private MechanicCrudService service = Factories.service.forMechanicCrudService();

    @Override
    public void execute() throws BusinessException {
    	String id = Console.readString("Type mechahic id to update");;
    	Optional<MechanicDto> optional = service.findById(id);
    	if (optional.isEmpty()) {
    	    Console.println("Mechanic not found");
    	    return;
    	}

    	MechanicDto m = optional.get();
    	m.name = Console.readString("Name");
    	m.surname = Console.readString("Surname");

    	service.update(m);
    	Console.println("Mechanic updated");

    }

    
}