package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {
    private String nif;

    public ListMechanicByNif(String nif) {
        if (nif == null) {
            throw new IllegalArgumentException("Receving null nif");
        }
        this.nif = nif;
    }

    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> m = Factories.persistence.forMechanic()
            .findByNif(nif);
        if (m.isPresent()) {
            MechanicDto o = MechanicAssembler.toDto(m.get());
            return Optional.ofNullable(o);
        }
        return Optional.empty();
    }
}
