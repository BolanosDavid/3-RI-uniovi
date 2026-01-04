package uo.ri.cws.application.persistence.payroll;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {
    /**
     * findAllForMechanic: Busca todas las nóminas de un mecanico
     *
     * @param mechanicId String - id única del mecánico
     * @return List<PayrollRecord>
     *
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         PayrollGateway.findPayrollsOfMechanicById(id);
     */
    public List<PayrollRecord> findAllForMechanic(String mechanicId)
        throws PersistenceException;

    /**
     * findAllPayrollsOfLastMonth: Busca todas las nóminas del último mes
     *
     * @param from Date, to Date : Rangos de fechas del último mes.
     * @return List<PayrollRecord>
     *
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         PayrollGateway.findAllPayrollsOfLastMonth();
     */
    public List<PayrollRecord> findAllPayrollsOfLastMonth(Date from, Date to);

    /**
     * findLastMonthOfMechanic: Busca todas las nóminas del último mes de un
     * mecanico en específico
     *
     * @param from Date, to Date : Rangos de fechas del último mes.
     * @return List<PayrollRecord>
     *
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         PayrollGateway.findLastMonthOfMechanic();
     */
    public List<PayrollRecord>
            findLastMonthOfMechanic(String mechanicId, Date from, Date to);

    /**
     * findAllByProfGroupName: Busca todas las nóminas de un grupo profesional
     * en específico
     * 
     * @param name String: Nombre del grupo profesional
     * @return List<PayrollRecord>
     *
     *         Ejemplo de uso: List<PayrollRecord> lista =
     *         PayrollGateway.findAllByProfGroupName(name);
     */
    public List<PayrollRecord> findAllByProfGroupName(String name);

    /**
     * findContractBetween: Busca un contrato con la id pasada por parametro
     * entre las fechas pasadas por parametro
     * 
     * @param contractId String, from Date, to Date.
     * @return Optional<PayrollRecord>
     *
     *         Ejemplo de uso: Optional<PayrollRecord> opr =
     *         PayrollGateway.findContractBetween(name);
     */
    public Optional<PayrollRecord>
            findContractBetween(String contractId, Date from, Date to);

    public class PayrollRecord extends BasicRecord {
        public Double baseSalary;
        public LocalDate date;
        public Double extraSalary;
        public Double nicDeduction;
        public Double taxDeduction;
        public Double trienniumEarning;
        public Double productivityEarning;

        // might be null
        public String contractId;

    }

}
