package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        // Get info
        String id = Console.readString("Type mechahic id to update");
        MechanicDto m = Factories.service.forMechanicCrudService()
            .findById(id)
            .get();
        Factories.service.forMechanicCrudService()
            .update(m);
        Console.println("Mechanic updated");
    }

}