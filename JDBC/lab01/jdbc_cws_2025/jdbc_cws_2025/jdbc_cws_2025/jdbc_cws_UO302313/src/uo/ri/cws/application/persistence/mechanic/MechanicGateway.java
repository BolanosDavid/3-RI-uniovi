package uo.ri.cws.application.persistence.mechanic;

import java.util.Optional;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;;

public interface MechanicGateway extends Gateway<MechanicRecord> {
    /**
     * findByNif: busca en la tabla mecánicos un nif específico
     *
     * @param nif String: nif del mecánico
     * @return Optional<MechanicRecord>
     *
     *         Ejemplo de uso: Optional<MechanicRecord> mechanic =
     *         mechanicGateway.findByNif(nif_valido);
     */
    public Optional<MechanicRecord> findByNif(String nif);

    public class MechanicRecord extends BasicRecord {
        public String name;
        public String surname;
        public String nif;
    }

}
