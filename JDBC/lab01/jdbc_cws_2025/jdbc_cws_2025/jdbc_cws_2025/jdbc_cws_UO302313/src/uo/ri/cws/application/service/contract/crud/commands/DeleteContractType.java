package uo.ri.cws.application.service.contract.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {
    String name;

    public DeleteContractType(String name) {
        checkParamsValidity(name);
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        checkExists();
        Factories.persistence.forContractType()
            .remove(name);
        return null;
    }

    private void checkExists() throws BusinessException {
        Optional<ContractTypeRecord> p = Factories.persistence.forContractType()
            .findByName(name);
        if (p.isEmpty())
            throw new BusinessException("Name not in database");
        List<ContractRecord> c = Factories.persistence.forContract()
            .findByContractTypeId(p.get().id);
        if (!c.isEmpty())
            throw new BusinessException(
                    "Contract type has contracts associated");
    }

    private void checkParamsValidity(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new IllegalArgumentException("Receiving invalid name");
    }
}
