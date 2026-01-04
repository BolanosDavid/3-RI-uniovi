package uo.ri.cws.application.service.contract.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.exception.BusinessException;

public class ListAllContractTypes implements Command<List<ContractTypeDto>> {

    @Override
    public List<ContractTypeDto> execute() throws BusinessException {
        return Factories.persistence.forContractType()
            .findAll()
            .stream()
            .map(ContractTypeAssembler::toDto)
            .toList();
    }

}
