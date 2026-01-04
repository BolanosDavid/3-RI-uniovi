package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository mr = Factories.repository.forMechanic();

    public DeleteMechanic(
			  String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId,
				  "DeleteMechanic:: receiving blank mechanic Id");
	ArgumentChecks.isNotEmpty(mechanicId,
				  "DeleteMechanic:: receiving empty mechanic Id");
	this.mechanicId = mechanicId;
    }

    public Void
	   execute() throws BusinessException {
	Mechanic mecanico = checkMechanicState();
	mr.remove(mecanico);
	return null;
    }

    private Mechanic
	    checkMechanicState() throws BusinessException {
	Optional<Mechanic> mecanicoOptional = mr.findById(mechanicId);
	BusinessChecks.exists(mecanicoOptional,
			"DeleteMechanic:: No mechanic found with that id");
	Mechanic mecanico = mecanicoOptional.get();
	BusinessChecks.isTrue(mecanico.getInterventions()
				      .isEmpty(),
			"DeleteMechanic:: Mechanic has interventions");
	BusinessChecks.isTrue(mecanico.getAssigned()
				      .isEmpty(),
			"DeleteMechanic:: Mechanic has work orders asigned");
	BusinessChecks.doesNotExist(mecanico.getContractInForce(),
			"DeleteMechanic:: mechanic has a contract in force");
	BusinessChecks.isFalse(mecanico.getContracts()
			.stream()
			.anyMatch(c -> 
				c.getState() == ContractState.TERMINATED),
			"DeleteMechanic:: mechanic has terminated contracts");
	return mecanico;
    }

}
