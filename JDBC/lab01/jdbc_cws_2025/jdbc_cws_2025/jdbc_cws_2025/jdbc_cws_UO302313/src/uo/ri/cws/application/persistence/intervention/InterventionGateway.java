package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    /**
     * existsInterventionForMechanicId: busca en la tabla de intervenciones si
     * hay alguna intervención asociada a un mecánico
     *
     * @param id String - id del mecánico
     * @return Optional<InterventionRecord>
     *
     *         Ejemplo de uso: Optional<InterventionRecord> ir =
     *         interventionGateway.existsInterventionForMechanicId(id);
     */
    public Optional<InterventionRecord>
            existsInterventionForMechanicId(String id);

    public class InterventionRecord extends BasicRecord {
        public int minutes;
        public String mechanicId;
        public String workOrderId;
        public LocalDateTime date;

    }

}
