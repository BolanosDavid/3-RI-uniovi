package uo.ri.cws.application.persistence.contract;

import java.sql.Date;
import java.util.List;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {
    /**
     * findActiveAt: Busca todos los contratos activos en una fecha concreta
     *
     * @param from: Date - fecha de la que buscar
     * @return List<ContractRecord>
     *
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         ContractRecord.findActiveAt(from);
     */
    public List<ContractRecord> findActiveAt(Date from);

    /**
     * findTerminatedBetween: Busca todos los contratos terminados entre un
     * periodo concreto
     * 
     * @param from: Date, to:Date Rango de fechas por las que buscar
     * @return List<ContractRecord>
     *
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         ContractGateway.findTerminatedBetween(from);
     */
    public List<ContractRecord> findTerminatedBetween(Date from, Date to);

    /**
     * findByContractTypeId: Busca todos los contratos que tengan un id de tipo
     * de contrato en especifico
     * 
     * @param id: String id del tipo de contrato
     * @return List<ContractRecord>
     *
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         ContractGateway.findByContractTypeId(from);
     */
    public List<ContractRecord> findByContractTypeId(String id);

    /**
     * findContractedMechanics: Busca a todos los mecanicos con contratos en
     * vigor
     * 
     * @return List<ContractRecord>
     *
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         ContractGateway.findContractedMechanics(from);
     */
    public List<ContractRecord> findContractedMechanics();

    /**
     * findContracteForMechanic: Busca un contrato para un mecánico en
     * específico
     * 
     * @return List<ContractRecord>
     *
     *         Ejemplo de uso: List<ContractRecord> lista =
     *         ContractGateway.findContractedMechanics(from);
     */
    public List<ContractRecord> findContractForMechanic(String mechanicId);

    public class ContractRecord extends BasicRecord {
        public Double annualBaseSalary;
        public Date startDate;
        public Date endDate;
        public Double settlement;
        public String state;
        public Double taxRate;
        public String contractTypeId;
        public String mechanicId;
        public String professionalGroupId;

    }

}
