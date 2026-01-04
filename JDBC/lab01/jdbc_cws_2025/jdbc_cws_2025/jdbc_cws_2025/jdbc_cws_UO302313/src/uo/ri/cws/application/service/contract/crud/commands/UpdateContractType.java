package uo.ri.cws.application.service.contract.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeAssembler;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {
    private ContractTypeDto d;
    private ContractTypeGateway ctg = Factories.persistence.forContractType();

    public UpdateContractType(ContractTypeDto d) {
        checkParamsValidity(d);
        this.d = d;
    }

    @Override
    public Void execute() throws BusinessException {
        checkExists();
        ctg.update(ContractTypeAssembler.toRecord(d));
        return null;
    }

    private void checkExists() throws BusinessException {
        ctg.findByName(d.name)
            .orElseThrow(() -> new BusinessException(
                    "No contract type found for that name"));

    }

    private void checkParamsValidity(ContractTypeDto d) {
        if (d == null)
            throw new IllegalArgumentException("Receiving null dto");
        if (d.name == null || d.name.isBlank() || d.name.isEmpty())
            throw new IllegalArgumentException("Receiving invalid name");
        if (d.compensationDays < 0)
            throw new IllegalArgumentException(
                    "Receiving invalid compesation days");
    }

}
