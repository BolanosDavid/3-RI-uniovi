package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {
	private ProfessionalGroupDto dto;
	private ProfessionalGroupGateway pgw = Factories.persistence.forProfessionalGroup();

	public UpdateProfessionalGroup(ProfessionalGroupDto dto) {
		ArgumentChecks.isNotNull(dto, "The professional group to update cannot be null");
		ArgumentChecks.isNotBlank(dto.name, "The name cannot be blank");
		ArgumentChecks.isNotBlank(dto.id, "The id cannot be blank");
		ArgumentChecks.isTrue(dto.trienniumPayment >= 0, "Triennium payment cannot be negative");
		ArgumentChecks.isTrue(dto.productivityRate >= 0, "Productivity rate cannot be negative");
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> pr = pgw.findById(dto.id);
		if(pr.isEmpty()) {
			throw new BusinessException("Professional group does not exists");
		}
		ProfessionalGroupRecord readFromDataBase = pr.get();
        BusinessChecks.hasVersion(dto.version,readFromDataBase.version,"Staled data");
		pgw.update(ProfessionalGroupAssembler.toRecord(dto));
		return null;
	}

}
