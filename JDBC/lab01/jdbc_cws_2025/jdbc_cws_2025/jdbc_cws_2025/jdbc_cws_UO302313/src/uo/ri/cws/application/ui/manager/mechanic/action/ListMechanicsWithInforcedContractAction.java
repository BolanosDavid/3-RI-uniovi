package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.menu.Action;

public class ListMechanicsWithInforcedContractAction implements Action {

    @Override
    public void execute() throws Exception {

        ContractCrudService cs =
            Factories.service.forContractCrudService();
        MechanicCrudService ms =
            Factories.service.forMechanicCrudService();

        List<String> ids = cs.findInforceContracts()
            .stream()
            .map(c -> c.mechanic.id)
            .filter(Objects::nonNull)
            .distinct()
            .toList();

        List<MechanicDto> mechanics = new ArrayList<>();

        for (String id : ids) {

            Optional<MechanicDto> opt = ms.findById(id);
            opt.ifPresent(mechanics::add);

        }

        mechanics.forEach(Printer::printMechanic);
    }

}
