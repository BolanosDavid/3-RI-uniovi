package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;

import java.util.Optional;

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
	checkParams(dto);
	this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
	Mechanic mechanic = new Mechanic(dto.nif, dto.surname, dto.name);
	Optional<Mechanic> mechanicOptional = mr.findByNif(dto.nif);
	BusinessChecks.doesNotExist(mechanicOptional,
			"AddMechanic:: Already mechanic with that nif");
	mr.add(mechanic);

	return DtoAssembler.toDto(mechanic);
    }

    /**
     * Checks all received parameters
     * 
     * @param dto
     */
    private void checkParams(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto, "AddMechanic:: receiving null dto");
	ArgumentChecks.isNotEmpty(dto.name,
			"AddMechanic:: invalid name");
	ArgumentChecks.isNotEmpty(dto.surname,
			"AddMechanic::  invalid surname");
	ArgumentChecks.isNotEmpty(dto.nif,
			"AddMechanic::  invalid nif");
    }
}
