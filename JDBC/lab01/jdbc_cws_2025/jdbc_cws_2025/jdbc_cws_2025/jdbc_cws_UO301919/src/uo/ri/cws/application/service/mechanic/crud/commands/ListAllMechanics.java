package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListAllMechanics implements Command<List<MechanicDto>>{
	
	 private MechanicGateway mg = Factories.persistence.forMechanic();
	 
	public List<MechanicDto> execute() {
		List<MechanicDto> result = new ArrayList<>();
        List<MechanicRecord> records = mg.findAll();

        for (MechanicRecord r : records) {
            MechanicDto dto = MechanicAssembler.toDto(r);
            result.add(dto);
        }

        return result;
	}
}
