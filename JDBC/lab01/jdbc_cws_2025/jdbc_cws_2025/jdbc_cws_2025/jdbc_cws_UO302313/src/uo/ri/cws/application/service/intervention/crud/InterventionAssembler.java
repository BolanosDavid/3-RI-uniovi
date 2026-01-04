package uo.ri.cws.application.service.intervention.crud;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.service.intervention.InterventionCrudService.InterventionDto;;

public class InterventionAssembler {
    public static InterventionRecord toRecord(InterventionDto m) {
        InterventionRecord r = new InterventionRecord();
        r.id = m.id;
        r.version = m.version;
        r.minutes = m.minutes;
        r.date = m.date;
        r.mechanicId = m.mechanicId;
        r.workOrderId = m.workOrderId;
        return r;
    }

}
