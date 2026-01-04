package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private String mechanicId;

    public DeleteMechanic(String mechanicId) throws BusinessException {
        if (mechanicId == null) {
            throw new IllegalArgumentException("MechanicId cannot be null");
        }

        this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
        checkMechanicStatus();
        mg.remove(mechanicId);
        return null;
    }

    private void checkMechanicStatus() throws BusinessException {
        Optional<InterventionRecord> ig =
            Factories.persistence.forIntervention()
                .existsInterventionForMechanicId(mechanicId);
        List<WorkOrderRecord> wg = Factories.persistence.forWorkOrder()
            .findByMechanicId(mechanicId);
        Optional<MechanicRecord> mr = mg.findById(mechanicId);
        List<ContractRecord> crL = Factories.persistence.forContract()
            .findContractForMechanic(mechanicId);

        BusinessChecks.exists(mr, "No mechanic found for that Id");
        BusinessChecks.doesNotExist(ig, "Mechanic has interventions");
        BusinessChecks.isEmpty(wg, "Mechanic has workorders");
        BusinessChecks.isEmpty(crL, "Mechanic has contract in force");
    }

}
