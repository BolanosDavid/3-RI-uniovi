package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.UUID;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto>{
	private MechanicDto m;
	private MechanicGateway mg = Factories.persistence.forMechanic();
	
	public AddMechanic(MechanicDto arg) {
		ArgumentChecks.isNotNull(arg, "The mechanic cannot be null");
		ArgumentChecks.isNotBlank(arg.nif, "The mechanic nif cannot be blank");
		ArgumentChecks.isNotBlank(arg.name, "The mechanic name cannot be blank");
		ArgumentChecks.isNotBlank(arg.surname, "The mechanic surname cannot be blank");
		m = arg;
		m.id = UUID.randomUUID().toString();
        m.version = 1;
	}
	public MechanicDto execute() throws BusinessException {
		if(mg.findByNif(m.nif).isPresent()) {
			throw new BusinessException("The mechanic already exists.");
		}
		MechanicRecord mr = MechanicAssembler.toRecord(m);
		mg.add(mr);
		return m;
	}
	
}
