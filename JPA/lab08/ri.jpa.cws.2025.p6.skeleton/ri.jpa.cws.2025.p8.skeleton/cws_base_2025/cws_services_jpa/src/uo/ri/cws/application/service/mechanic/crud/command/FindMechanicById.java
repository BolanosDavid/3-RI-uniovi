package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;

	public FindMechanicById(String id) {
	    ArgumentChecks.isNotEmpty(id,"FindMechanicById:: recibiendo id no valida");
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
	    return Factories.repository.forMechanic().findById(id).map(DtoAssembler::toDto);
	}

}
