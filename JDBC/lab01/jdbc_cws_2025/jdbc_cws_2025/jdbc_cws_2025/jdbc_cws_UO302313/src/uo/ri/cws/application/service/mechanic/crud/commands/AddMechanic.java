package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto m;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public AddMechanic(MechanicDto m) throws BusinessException {
        checkArgumentsValidity(m);
        this.m = m;
        this.m.id = UUID.randomUUID()
            .toString();
        this.m.version = 1;

    }

    public MechanicDto execute() throws BusinessException {
        Optional<MechanicRecord> mr = mg.findByNif(m.nif);
        BusinessChecks.doesNotExist(mr,
            "Existing mechanic with that nif");
        mg.add(MechanicAssembler.toRecord(m));
        return m;
    }

    private void checkArgumentsValidity(MechanicDto m)
        throws IllegalArgumentException {
        if (m == null) {
            throw new IllegalArgumentException("Mechanic cannot be null");
        }
        if (m.nif == null) {
            throw new IllegalArgumentException("Mechanic nif cannot be null");
        }
        if (m.nif.isEmpty())
            throw new IllegalArgumentException("Nif cannot be empy");
        if (m.nif.isBlank())
            throw new IllegalArgumentException("Nif cannot be blank");
    }

}
