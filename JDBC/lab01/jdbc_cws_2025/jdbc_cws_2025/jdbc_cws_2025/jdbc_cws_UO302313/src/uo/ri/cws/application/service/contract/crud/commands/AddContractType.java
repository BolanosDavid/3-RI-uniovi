package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto> {
    ContractTypeDto d;

    public AddContractType(ContractTypeDto d) {
        checkArgumentsValidity(d);
        this.d = d;
        d.id = UUID.randomUUID()
            .toString();
    }

    private void checkArgumentsValidity(ContractTypeDto d) {
        if (d == null)
            throw new IllegalArgumentException("Receving null contract type");
        if (d.compensationDays < 0)
            throw new IllegalArgumentException(
                    "Receiving < 0 compensation days");
        if (d.name == null || d.name.isBlank() || d.name.isEmpty())
            throw new IllegalArgumentException("Receiving invalid name");
    }

    @Override
    public ContractTypeDto execute() throws BusinessException {
        checkExists();
        Factories.persistence.forContractType()
            .add(ContractTypeAssembler.toRecord(d));
        return d;

    }

    private void checkExists() throws BusinessException {
        Optional<ContractTypeRecord> p = Factories.persistence.forContractType()
            .findByName(d.name);
        if (p.isPresent())
            throw new BusinessException("Name already exists");
    }

}
