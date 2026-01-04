package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository mr= Factories.repository.forMechanic();
    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotEmpty(mechanicId);
	this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
	Optional<Mechanic> mecanicoOptional = mr.findById(mechanicId);
	BusinessChecks.exists(mecanicoOptional,
			"DeleteMechanic:: No mechanic found with that id");
	Mechanic mecanico = mecanicoOptional.get();
	mr.remove(mecanico);
	BusinessChecks.isTrue(mecanico.getInterventions().isEmpty(),
			"DeleteMechanic:: Mechanic has interventions");
	BusinessChecks.isTrue(mecanico.getAssigned().isEmpty(),
			"DeleteMechanic:: Mechanic has work orders asigned");

	return null;
    }

}
