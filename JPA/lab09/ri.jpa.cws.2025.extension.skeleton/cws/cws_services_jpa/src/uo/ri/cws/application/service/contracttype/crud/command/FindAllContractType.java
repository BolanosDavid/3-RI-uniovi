package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.exception.BusinessException;

public class FindAllContractType implements Command<List<ContractTypeDto>>  {

    @Override
    public List<ContractTypeDto> execute() throws BusinessException {
	List<ContractType> c = Factories.repository.forContractType().findAll();
	List<ContractTypeDto> result = new ArrayList<>();
	c.stream().map(DtoAssembler::toDto).forEach(result::add);
	return result;
    }

}
