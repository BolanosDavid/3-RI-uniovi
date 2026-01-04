package uo.ri.cws.application.service.contract.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractAssembler;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;

public class FindInForceContracts implements Command<List<ContractDto>>{
	private ContractGateway gateway = Factories.persistence.forContract();

    @Override
    public List<ContractDto> execute() {
        List<ContractRecord> records = gateway.findInForce();
        return ContractAssembler.toDtoList(records);
    }

}
