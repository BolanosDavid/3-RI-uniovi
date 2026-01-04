package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway
        extends Gateway<ProfessionalGroupRecord> {
    /**
     * findByName: busca en la tabla grupo profesional un nombre específico
     *
     * @param nif String: nombre del grupo profesional
     * @return List<ProfessionalGroupRecord>
     *
     *         Ejemplo de uso: List<ProfessionalGroupRecord> l =
     *         ProfessionalGroupGateway.findByName(name);
     */
    public Optional<ProfessionalGroupRecord> findByName(String name);

    public class ProfessionalGroupRecord extends BasicRecord {
        public String name;
        public Double productivityRate;
        public Double trienniumPayment;
    }

}
