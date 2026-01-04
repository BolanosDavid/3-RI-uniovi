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
        String nif = Console.readString("nif");
        Console.println("\nMechanic information \n");
        Optional<MechanicDto> m = Factories.service.forMechanicCrudService()
            .findByNif(nif);
        if (m.isPresent()) {
            Printer.printMechanic(m.get());
        } else {
            Console.print("There is no mechanic with this nif");
        }

    }
}