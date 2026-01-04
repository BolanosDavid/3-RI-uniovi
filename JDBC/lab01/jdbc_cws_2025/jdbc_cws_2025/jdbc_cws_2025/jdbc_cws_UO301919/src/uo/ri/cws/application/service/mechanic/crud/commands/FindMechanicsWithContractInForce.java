package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.exception.BusinessException;

public class FindMechanicsWithContractInForce implements Command<List<MechanicDto>>{

	private ContractGateway cg = Factories.persistence.forContract();
    private MechanicGateway mg = Factories.persistence.forMechanic();
	
    @Override
    public List<MechanicDto> execute() throws BusinessException {
        List<MechanicDto> result = new ArrayList<>();
        List<ContractRecord> contracts = cg.findInForce();

        for (ContractRecord c : contracts) {
            Optional<MechanicRecord> mechanic = mg.findById(c.mechanicId);
            if (mechanic.isPresent()) {
                result.add(MechanicAssembler.toDto(mechanic.get()));
            }
        }

        return result;
    }


}
