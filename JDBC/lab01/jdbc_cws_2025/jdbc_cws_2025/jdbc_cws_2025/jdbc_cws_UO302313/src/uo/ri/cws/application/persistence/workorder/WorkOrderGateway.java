package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {
    /**
     * findByMechanicId: busca en la tabla de workorders filtrando por la id del
     * mecánico
     *
     * @param mechanicId String: id del mecánico
     * @return Optional<WorkOrderRecord>
     *
     *         Ejemplo de uso: Optional<WorkOrderRecord> p =
     *         WorkOrderGateway.findByMechanicId(name);
     */
    public List<WorkOrderRecord> findByMechanicId(String mechanicId);

    /**
     * findByInterventionMechanicId: busca en la tabla de workorders e
     * intervenciones todas aquellas asociadas a un mecánico en concreto
     *
     * @param mechanicId String: id del mecánico
     * @return Optional<WorkOrderRecord>
     *
     *         Ejemplo de uso: Optional<WorkOrderRecord> p =
     *         WorkOrderGateway.findByInterventionMechanicId(name);
     */
    public List<WorkOrderRecord>
            findByInterventionMechanicId(String mechanicId);

    public class WorkOrderRecord extends BasicRecord {
        public String vehicleId;
        public String description;
        public LocalDateTime date;
        public double amount;
        public String state;
        // might be null
        public String mechanicId;
        public String invoiceId;

    }
}
