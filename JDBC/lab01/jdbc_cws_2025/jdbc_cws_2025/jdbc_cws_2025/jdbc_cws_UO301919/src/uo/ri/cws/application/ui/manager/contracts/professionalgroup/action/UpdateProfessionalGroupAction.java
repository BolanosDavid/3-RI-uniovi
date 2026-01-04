package uo.ri.cws.application.ui.manager.contracts.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String name = Console.readString("Professional group name");

        Optional<ProfessionalGroupDto> result = Factories.service.forProfessionalGroupCrudService()
            .findByName(name);

        if (result.isEmpty()) {
            Console.println("No professional group found with name: " + name);
            return;
        }

        double trienniumPayment = Console.readDouble("New triennium payment");
        double productivityRate = Console.readDouble("New productivity rate");

        ProfessionalGroupDto dto = result.get();
        dto.trienniumPayment = trienniumPayment;
        dto.productivityRate = productivityRate;

        Factories.service.forProfessionalGroupCrudService().update(dto);

        Console.println("Professional group updated");
    }
}