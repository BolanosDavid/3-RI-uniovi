package uo.ri.cws.application.service.professionalgroup.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {
	private String name;
	private ProfessionalGroupGateway pgw = Factories.persistence.forProfessionalGroup();
	private ContractGateway cg = Factories.persistence.forContract();

	public DeleteProfessionalGroup(String name) {
		ArgumentChecks.isNotBlank(name, "The name to delete cannot be blank");
		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> professionalGroup = pgw.findByName(name);
		if(professionalGroup.isEmpty()) {
			throw new BusinessException("The professional group does not exists");
		}
		String groupID = professionalGroup.get().id;
		if(cg.existsContractsForProfessionalGroup(groupID)) {
			throw new BusinessException("Cannot delete a professional group with associated contracts");
		}
		pgw.remove(groupID);
		return null;
	}

}
