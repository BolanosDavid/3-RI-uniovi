package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void>{
	private String id;
	private MechanicGateway mg = Factories.persistence.forMechanic();
	
	public DeleteMechanic(String id) {
		ArgumentChecks.isNotNull(id);
	    ArgumentChecks.isNotBlank(id);
		this.id = id;
	}
	
	public Void execute() throws BusinessException {
		if(mg.findById(id).isEmpty()) {
			throw new BusinessException("The mchanic does not exists.");
		} 
		// Gestión de mecánicos ampliado
		 if(!Factories.persistence.forWorkOrder().findByMechanic(id).isEmpty()) {
			throw new BusinessException("The mechanic to delete has assigned work orders");
		} if(!Factories.persistence.forIntervention().findByMechanic(id).isEmpty()) {
			throw new BusinessException("The mechanic to delete has assigned interventions");
		} if(!Factories.persistence.forContract().findByMechanic(id).isEmpty()) {
			throw new BusinessException("The mechanic to delete has assigned contracts");
		}
		mg.remove(id);
		return null;
	}

}
