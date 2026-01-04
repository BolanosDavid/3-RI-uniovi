package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractTypeByName implements
					    Command<Optional<ContractTypeDto>> {
    private ContractTypeRepository repo = Factories.repository
		    						.forContractType();
    private String name;
    public FindContractTypeByName(String name) {
	ArgumentChecks.isNotEmpty(name,
	                          "FindContractTypeByName:: name cannot"
	                          + " be empty");
	ArgumentChecks.isNotBlank(name,
	                          "FindContractTypeByName:: name cannot "
	                          + "be blank");
	this.name = name;
    }
	@Override
	public Optional<ContractTypeDto> execute() throws BusinessException {
	    Optional<ContractType> contractType = repo.findByName(name);
	    if(contractType.isPresent()) {
		return Optional.of(DtoAssembler.toDto(contractType.get()));
	    }else {
		return Optional.empty();
	    }
	}

}