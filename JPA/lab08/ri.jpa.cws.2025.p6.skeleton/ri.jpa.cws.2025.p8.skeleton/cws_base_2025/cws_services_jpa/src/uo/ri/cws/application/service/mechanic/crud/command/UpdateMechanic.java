package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository repository= Factories.repository.forMechanic();
	public UpdateMechanic(MechanicDto dto) {
	    ArgumentChecks.isNotNull(dto,"");
	    ArgumentChecks.isNotEmpty(dto.name,"UpdateMechanic:: reciving empty name for the mechanic");
	    ArgumentChecks.isNotEmpty(dto.surname,"UpdateMechanic:: reciving empty surname for the mechanic");
	    ArgumentChecks.isNotEmpty(dto.nif,"UpdateMechanic:: reciving empty nif for the mechanic");
	    this.dto = dto;
	}

	public Void execute() throws BusinessException {
	   Optional<Mechanic> mecanicoOptional = repository.findById(dto.id);
	   BusinessChecks.exists(mecanicoOptional,"UpateMechanic:: no mechanic found with that id");
	   Mechanic mecanico = mecanicoOptional.get();
	   BusinessChecks.hasVersion(mecanico.getVersion(), dto.version,"UpdateMechanic:: mechanic has been updated meantime  ");
	   mecanico.setName(dto.name);
	   mecanico.setSurname(dto.surname);
	    return null;
	}

}
