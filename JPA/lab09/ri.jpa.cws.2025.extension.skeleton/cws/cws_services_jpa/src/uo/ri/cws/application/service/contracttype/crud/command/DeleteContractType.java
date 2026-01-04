package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {
    private ContractTypeRepository repo = Factories.repository
		    .forContractType();
    private String name;

    public DeleteContractType(String name) {
	ArgumentChecks.isNotBlank(name,
			"DeleteContractType:: receiving blank contract type name");
	ArgumentChecks.isNotEmpty(name,
			"DeleteContractType:: receiving empty contract type name");
	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ContractType> c  = repo.findByName(name);
	BusinessChecks.exists(c,
	                      "DeleteContractType:: no contract type"
	                      + " found with that name");
	BusinessChecks.isTrue(c.get().getContracts().isEmpty(), 
	                      "DeleteContractType:: contract type has contracts"
	                      + "associated");
	repo.remove(c.get());
	return null;
    }

}