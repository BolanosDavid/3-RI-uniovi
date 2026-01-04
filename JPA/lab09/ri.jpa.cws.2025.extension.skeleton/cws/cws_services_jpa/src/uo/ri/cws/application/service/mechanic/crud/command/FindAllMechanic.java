package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;  
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllMechanic implements Command<List<MechanicDto>> {
	public List<MechanicDto> execute() {
	    return Factories.repository.forMechanic()
			    .findAll().stream().map(DtoAssembler::toDto)
			    .toList();
	}

}
