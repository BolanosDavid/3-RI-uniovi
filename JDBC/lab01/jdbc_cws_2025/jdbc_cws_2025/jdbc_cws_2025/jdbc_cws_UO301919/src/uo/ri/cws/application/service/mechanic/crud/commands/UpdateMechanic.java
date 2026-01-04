package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void>{
	
    private MechanicDto m;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    
    public UpdateMechanic(MechanicDto arg) {
    	ArgumentChecks.isNotNull(arg, "The mechanic cannot be null");
    	ArgumentChecks.isNotBlank(arg.id);
    	ArgumentChecks.isNotBlank(arg.nif);
    	ArgumentChecks.isNotBlank(arg.name);
    	ArgumentChecks.isNotBlank(arg.surname);
    	this.m = arg;
    }
    
    public Void execute() throws BusinessException {
    	MechanicRecord readFromDataBase = checkMechanicExists(m.id);
        BusinessChecks.hasVersion(m.version,readFromDataBase.version,"Staled data");
        updateMechanic(m);
    	return null;
    }
    
    private void updateMechanic(MechanicDto m) {
    	mg.update(MechanicAssembler.toRecord(m));
    }

    private MechanicRecord checkMechanicExists(String id) throws BusinessException {
    	Optional<MechanicRecord> record = mg.findById(id);
        BusinessChecks.isTrue(record.isPresent(),"Mechanic does not exists");
        return record.get();
    }
  
}
