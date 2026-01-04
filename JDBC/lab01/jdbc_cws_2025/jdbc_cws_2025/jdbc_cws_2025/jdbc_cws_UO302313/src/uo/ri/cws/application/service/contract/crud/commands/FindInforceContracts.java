package uo.ri.cws.application.service.contract.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.ContractAssembler;
import uo.ri.util.exception.BusinessException;

public class FindInforceContracts implements Command<List<ContractDto>> {

    @Override
    public List<ContractDto> execute() throws BusinessException {
        return Factories.persistence.forContract()
            .findContractedMechanics()
            .stream()
            .map(ContractAssembler::toDto)
            .toList();
    }

}
