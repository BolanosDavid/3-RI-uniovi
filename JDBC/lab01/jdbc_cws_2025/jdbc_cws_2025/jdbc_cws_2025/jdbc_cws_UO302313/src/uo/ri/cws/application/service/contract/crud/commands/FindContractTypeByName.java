package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.exception.BusinessException;

public class FindContractTypeByName
        implements Command<Optional<ContractTypeDto>> {
    String name;

    public FindContractTypeByName(String name) {
        checkArgumentsValidity(name);
        this.name = name;
    }

    @Override
    public Optional<ContractTypeDto> execute() throws BusinessException {
        Optional<ContractTypeRecord> p = Factories.persistence.forContractType()
            .findByName(name);
        if (p.isEmpty())
            return Optional.empty();
        return Optional.of(ContractTypeAssembler.toDto(p.get()));
    }

    private void checkArgumentsValidity(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new IllegalArgumentException("Receiving invalid name");
    }
}
