package uo.ri.cws.application.service.contracttype.crud.command;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto> {

    private ContractTypeRepository repo = Factories.repository
		    .forContractType();
    private ContractTypeDto dto;

    public AddContractType(ContractTypeDto dto) {
	
	checkParams(dto);
	this.dto = dto;
    }

    @Override
    public ContractTypeDto execute() throws BusinessException {
	ContractType c = new ContractType(dto.name, dto.compensationDays);
	BusinessChecks.doesNotExist(repo.findByName(dto.name), 
	                            "AddContractType:: trying to add an "
	                            + "existing contract type");
	repo.add(c);
	return DtoAssembler.toDto(c);
    }

    /**
     * Checks all received parameters
     * 
     * @param dto
     */
    private void checkParams(ContractTypeDto dto) {
	ArgumentChecks.isNotNull(dto,
	                         "AddContractType:: recibiendo dto null");
	ArgumentChecks.isNotEmpty(dto.name,
			"AddContractType:: recibiendo invalid name");
	ArgumentChecks.isNotBlank(dto.name,
	                          "AddContractType:: recibiendo blank name");
	ArgumentChecks.isTrue(dto.compensationDays >= 0,
			"AddContractType:: recibiendo invalid surname");

    }

}