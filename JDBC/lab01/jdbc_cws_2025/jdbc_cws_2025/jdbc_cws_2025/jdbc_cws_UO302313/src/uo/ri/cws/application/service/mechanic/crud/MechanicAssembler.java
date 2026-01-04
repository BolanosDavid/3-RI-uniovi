package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicAssembler {
    public static MechanicRecord toRecord(MechanicDto m) {
        MechanicRecord r = new MechanicRecord();
        r.name = m.name;
        r.id = m.id;
        r.surname = m.surname;
        r.nif = m.nif;
        r.version = m.version;
        return r;
    }

    public static MechanicDto toDto(MechanicRecord m) {
        MechanicDto r = new MechanicDto();
        r.name = m.name;
        r.id = m.id;
        r.surname = m.surname;
        r.nif = m.nif;
        r.version = m.version;
        return r;

    }
}
