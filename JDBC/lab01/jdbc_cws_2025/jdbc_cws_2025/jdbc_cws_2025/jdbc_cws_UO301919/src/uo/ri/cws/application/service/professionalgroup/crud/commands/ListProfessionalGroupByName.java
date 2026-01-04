package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.ProfessionalGroupAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListProfessionalGroupByName implements Command<Optional<ProfessionalGroupDto>> {
	private String name;
	private ProfessionalGroupGateway pgw = Factories.persistence.forProfessionalGroup();
	
	public ListProfessionalGroupByName(String name) {
		ArgumentChecks.isNotBlank(name, "The professional group name cannot be blank");
		this.name = name;
	}

	@Override
	public Optional<ProfessionalGroupDto> execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> pr = pgw.findByName(name);	   
	    if (pr.isEmpty()) {
	        return Optional.empty();
	    }
	    ProfessionalGroupDto dto = ProfessionalGroupAssembler.toDto(pr.get());
	    return Optional.ofNullable(dto);
	}

}
