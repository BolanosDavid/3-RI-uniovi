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
    private MechanicRepository repository = Factories.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	checkParams(dto);
	this.dto = dto;
    }

    public Void execute() throws BusinessException {
	Optional<Mechanic> mecanicoOptional = repository.findById(dto.id);
	BusinessChecks.exists(mecanicoOptional,
			"UpateMechanic:: no mechanic found with that id");
	
	Mechanic mecanico = mecanicoOptional.get();
	BusinessChecks.hasVersion(mecanico.getVersion(), dto.version,
			"UpdateMechanic:: mechanic has been updated meantime");
	mecanico.setName(dto.name);
	mecanico.setSurname(dto.surname);
	mecanico.updatedNow();
	return null;
    }

    private void checkParams(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto,
	                         "UpdateMechanic:: reciving null dto");
	ArgumentChecks.isNotEmpty(dto.nif, 
	                          "UpdateMechanic:: not valid nif");
	ArgumentChecks.isNotEmpty(dto.surname,
			"UpdateMechanic:: not valid surname");
	ArgumentChecks.isNotEmpty(dto.name,
	                          "UpdateMechanic:: not valid name");
	ArgumentChecks.isNotBlank(dto.nif,
	                          "UpdateMechanic:: not valid nif");
	ArgumentChecks.isNotBlank(dto.surname,
			"UpdateMechanic:: not valid surname");
	ArgumentChecks.isNotBlank(dto.name,
	                          "UpdateMechanic:: not valid name");
    }
}
