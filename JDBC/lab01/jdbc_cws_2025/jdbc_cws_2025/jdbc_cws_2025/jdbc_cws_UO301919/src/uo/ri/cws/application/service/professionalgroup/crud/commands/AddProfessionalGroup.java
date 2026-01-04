package uo.ri.cws.application.service.professionalgroup.crud.commands;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import java.util.UUID;

import uo.ri.conf.Factories;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {

	private ProfessionalGroupDto dto;
	private ProfessionalGroupGateway pgw = Factories.persistence.forProfessionalGroup();
	
	public AddProfessionalGroup(ProfessionalGroupDto dto) {
		ArgumentChecks.isNotNull(dto, "The professional group to create can not be null");
		ArgumentChecks.isNotBlank(dto.name, "The name cannot be blank");
		ArgumentChecks.isTrue(dto.trienniumPayment >= 0, "The triiennium payment cannot be negative");
		ArgumentChecks.isTrue(dto.productivityRate >= 0, "The productivity rate cannot be negative");
		this.dto = dto;
	}

	@Override
	public ProfessionalGroupDto execute() throws BusinessException {
	    if (pgw.findByName(dto.name).isPresent()) {
	        throw new BusinessException("Professional group already exists");
	    }    
	    dto.version = 1L;
	    dto.id = UUID.randomUUID().toString();	    
	    ProfessionalGroupRecord pr = ProfessionalGroupAssembler.toRecord(dto);
	    pgw.add(pr);

	    return dto;
	}

}
