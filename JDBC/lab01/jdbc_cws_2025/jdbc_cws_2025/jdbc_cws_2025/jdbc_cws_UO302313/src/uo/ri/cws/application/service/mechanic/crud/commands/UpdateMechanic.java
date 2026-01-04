package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDto m;

    public UpdateMechanic(MechanicDto m) {
        checkArgumentsValidity(m);
        this.m = m;
    }

    public Void execute() throws BusinessException {
        MechanicRecord mr = checkMechanicStatus(m);
        mg.update(mr);
        return null;
    }

    private MechanicRecord checkMechanicStatus(MechanicDto m)
        throws BusinessException {
        MechanicRecord mr = MechanicAssembler.toRecord(m);
        Optional<MechanicRecord> existsCheck = mg.findById(m.id);
        BusinessChecks.exists(existsCheck,
            "No mechanic found for that Id");
        BusinessChecks.hasVersion(existsCheck.get().version,
            m.version,
            "Mechanic versions does not match");
        return mr;
    }

    private void checkArgumentsValidity(MechanicDto m) {
        if (m.surname == null)
            throw new IllegalArgumentException(
                    "Mechanic surname cannot be null");
        if (m.surname.isEmpty())
            throw new IllegalArgumentException(
                    "Mechanic surname cannot be empy");
        if (m.surname.isBlank())
            throw new IllegalArgumentException(
                    "Mechanic surname cannot be blank");
        if (m.name == null)
            throw new IllegalArgumentException("Mechanic name cannot be null");
        if (m.name.isEmpty())
            throw new IllegalArgumentException("Mechanic name cannot be empy");
        if (m.name.isBlank())
            throw new IllegalArgumentException("Mechanic name cannot be blank");
        if (m.nif == null)
            throw new IllegalArgumentException("Nif cannot be null");

    }

}
