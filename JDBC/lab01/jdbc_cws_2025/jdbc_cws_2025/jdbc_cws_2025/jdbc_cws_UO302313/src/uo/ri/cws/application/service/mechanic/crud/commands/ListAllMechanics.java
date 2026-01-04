package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class ListAllMechanics implements Command<List<MechanicDto>> {

    public List<MechanicDto> execute() {
        List<MechanicRecord> mechanics = Factories.persistence.forMechanic()
            .findAll();
        List<MechanicDto> listaFinal = new ArrayList<MechanicDto>();
        mechanics.stream()
            .map(MechanicAssembler::toDto)
            .forEach(listaFinal::add);
        return listaFinal;
    }

}
