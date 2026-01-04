package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
 
public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private MechanicRepository mr = Factories.repository.forMechanic();
	public AddMechanic(MechanicDto dto) {
	   ArgumentChecks.isNotNull(dto,"AddMechanic:: recibiendo dto null");
		this.dto = dto;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
	   Mechanic mechanic = new Mechanic(dto.nif,dto.name,dto.surname);
	   BusinessChecks.exists(mr.findByNif(dto.nif),"AddMechanic:: Already mechanic with that nif");
	   mr.add(mechanic);
	  
	   return DtoAssembler.toDto(mechanic);
	}

}
