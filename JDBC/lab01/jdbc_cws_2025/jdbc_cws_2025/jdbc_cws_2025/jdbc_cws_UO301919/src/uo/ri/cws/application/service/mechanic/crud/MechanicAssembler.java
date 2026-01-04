package uo.ri.cws.application.service.mechanic.crud;


import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicAssembler {
	
	public static MechanicRecord toRecord(MechanicDto m) {
		MechanicRecord r = new MechanicRecord();
		r.id = m.id;
		r.version = m.version;
		r.nif = m.nif;
		r.name = m.name;
		r.surname = m.surname;
		return r;
	}
	
	public static MechanicDto toDto(MechanicRecord r) {
	    MechanicDto dto = new MechanicDto();
	    dto.id = r.id;
	    dto.nif = r.nif;
	    dto.name = r.name;
	    dto.surname = r.surname;
	    dto.version = r.version;
	    return dto;
	}
	
}
