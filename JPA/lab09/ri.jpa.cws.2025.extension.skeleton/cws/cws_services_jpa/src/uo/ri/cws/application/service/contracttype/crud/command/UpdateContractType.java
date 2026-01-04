package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {
    private ContractTypeRepository repo = Factories.repository.forContractType();
    private ContractTypeDto dto;

    public UpdateContractType(
			      ContractTypeDto dto) {
	ArgumentChecks.isNotNull(dto,
				 "UpdateContractType:: receiving null "
				 + "contract type");
	ArgumentChecks.isNotEmpty(dto.name,
				  "UpdateContractType:: receiving dto with "
				  + "empty name");
	ArgumentChecks.isNotBlank(dto.name,
				  "UpdateContractType:: receiving dto with"
				  + " blank name");
	ArgumentChecks.isTrue(dto.compensationDays > 0
			,"UpdateContractType:: receiving invalid compensation"
					+ "days");
	this.dto = dto;
    }

    @Override
    public Void
	   execute() throws BusinessException {
	Optional<ContractType> c = repo.findById(dto.id);
	BusinessChecks.exists(c,
			      "UpdateContractType:: no contract type found "
			      + "with that id");
	ContractType cs = c.get();
	BusinessChecks.hasVersion(cs.getVersion(),
				  dto.version,
				  "UpdateContractType:: contract type has been "
				  + "updated meantime");
	cs.setCompensationDaysPerYear(dto.compensationDays);
	cs.updatedNow();
	return null;
    }
}
