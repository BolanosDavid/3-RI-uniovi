package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListMechanicById implements Command<Optional<MechanicDto>> {

    private String id;

    public ListMechanicById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Receving null id");
        }
        this.id = id;
    }

    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> m = Factories.persistence.forMechanic()
            .findById(id);
        if (m.isPresent()) {
            return Optional.ofNullable(MechanicAssembler.toDto(m.get()));
        }
        return Optional.empty();

    }
}
