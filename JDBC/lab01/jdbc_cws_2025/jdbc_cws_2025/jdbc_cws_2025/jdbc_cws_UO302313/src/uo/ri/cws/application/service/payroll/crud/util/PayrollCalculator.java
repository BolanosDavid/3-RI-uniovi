package uo.ri.cws.application.service.payroll.crud.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public final class PayrollCalculator {

    private PayrollCalculator() {
    }

    public static class Calculated {
        public double baseSalary;
        public double extraSalary;
        public double trienniumEarning;
        public double productivityEarning;
        public double taxDeduction;
        public double nicDeduction;
        public double grossSalary;
        public double totalDeductions;
        public double netSalary;
    }

    /**
     * Calcula a través de un record los valores: gross, deductions y net
     * 
     * @param r
     * @return
     */
    public static Calculated fromRecord(PayrollRecord r) {
        BigDecimal base = r3BD(r.baseSalary);
        BigDecimal extra = r3BD(r.extraSalary);
        BigDecimal tri = r3BD(r.trienniumEarning);
        BigDecimal prod = r3BD(r.productivityEarning);
        BigDecimal tax = r3BD(r.taxDeduction);
        BigDecimal nic = r3BD(r.nicDeduction);

        BigDecimal gross = base.add(extra)
            .add(tri)
            .add(prod);
        BigDecimal ded = tax.add(nic);
        BigDecimal net = gross.subtract(ded);

        Calculated c = new Calculated();
        c.baseSalary = base.doubleValue();
        c.extraSalary = extra.doubleValue();
        c.trienniumEarning = tri.doubleValue();
        c.productivityEarning = prod.doubleValue();
        c.taxDeduction = tax.doubleValue();
        c.nicDeduction = nic.doubleValue();
        c.grossSalary = gross.doubleValue();
        c.totalDeductions = ded.doubleValue();
        c.netSalary = net.doubleValue();
        return c;
    }

    /**
     * Calcula la nomina recibiendo como parametros: -El contrato -El grupo
     * profesional -Fecha de inicio -Fecha de fin -Ordenes de trabajo
     * 
     * @param c          : ContractRecord. Información del contrato
     * @param g:         ProfessionalGroupRecord. Información sobre el grupo
     *                   profesional
     * @param start      : LocalDate. Fecha de inicio de la nómina
     * @param end        : LocalDate. Fecha de fin de la nómina
     * @param workOrders : List<WorkOrderRecord>. Lista de ordenes de trabajo
     * @return cal : Calculated. DTO con todos los valores calculados
     */
    public static Calculated fromContract(ContractRecord c,
                                          ProfessionalGroupRecord g,
                                          LocalDate start,
                                          LocalDate end,
                                          List<WorkOrderRecord> workOrders) {
        double baseMonthly = c.annualBaseSalary / 14.0;
        double extra = isExtra(end) ? baseMonthly : 0.0;

        LocalDate cutoff = cutoffDate(c,
            end);
        long triennia = Math.max(0,
            ChronoUnit.YEARS.between(c.startDate.toLocalDate(),
                cutoff) / 3);
        double triennium = g.trienniumPayment * triennia;

        double monthSum = workOrders.stream()
            .filter(w -> {
                LocalDate open = w.date.toLocalDate();
                return !open.isBefore(start) && !open.isAfter(end)
                        && isInvoiced(w);
            })
            .mapToDouble(w -> w.amount)
            .sum();

        double productivity = g.productivityRate * monthSum;

        double gross = baseMonthly + extra + triennium + productivity;
        double nic = ( c.annualBaseSalary / 12.0 ) * 0.05;
        double tax = c.taxRate * gross;

        Calculated cal = new Calculated();
        cal.baseSalary = round2(baseMonthly);
        cal.extraSalary = round2(extra);
        cal.trienniumEarning = round2(triennium);
        cal.productivityEarning = round2(productivity);
        cal.nicDeduction = round2(nic);
        cal.taxDeduction = round2(tax);

        BigDecimal base = r3BD(cal.baseSalary);
        BigDecimal extr = r3BD(cal.extraSalary);
        BigDecimal tri = r3BD(cal.trienniumEarning);
        BigDecimal pro = r3BD(cal.productivityEarning);
        BigDecimal td = r3BD(cal.taxDeduction);
        BigDecimal nd = r3BD(cal.nicDeduction);

        BigDecimal grossBD = base.add(extr)
            .add(tri)
            .add(pro);
        BigDecimal dedBD = td.add(nd);
        BigDecimal netBD = grossBD.subtract(dedBD);

        cal.grossSalary = grossBD.doubleValue();
        cal.totalDeductions = dedBD.doubleValue();
        cal.netSalary = netBD.doubleValue();
        return cal;
    }

    /**
     * Comprueba si el mes es Junio o Diciembre donde se reciben las pagas extra
     * 
     * @param end: LocalDate. Fecha final de la nomina
     * @return true si es junio/diciembre false en caso contrario
     */
    private static boolean isExtra(LocalDate end) {
        Month m = end.getMonth();
        return m == Month.JUNE || m == Month.DECEMBER;
    }

    /**
     * devuelve el mínimo entre la fecha de fin de nomina y la fecha de fin del
     * contrato ya que enddate puede ser null en ciertos casos de contrato.
     * 
     * @param c:  ContractRecord. Información del contrato
     * @param end :LocalDate. Fecha fin de nómina
     * @return LocalDate con la fecha mínima
     */
    private static LocalDate cutoffDate(ContractRecord c,
                                        LocalDate end) {
        return ( c.endDate == null || c.endDate.toLocalDate()
            .isAfter(end) ) ? end : c.endDate.toLocalDate();
    }

    /**
     * Comprueba si una work order está en estado Invoiced
     * 
     * @param w: WorkOrderRecord. Orden de trabajo a comprobar
     * @return true si esta en estado Invoiced false si no
     */
    private static boolean isInvoiced(WorkOrderRecord w) {
        return "INVOICED".equals(w.state);
    }

    /**
     * Redondeo HALF_UP a 3 decimales
     * 
     * @param v: Double. Numero a aproximar
     * @return BigDecimal: Numero aproximado
     */
    private static BigDecimal r3BD(double v) {
        return new BigDecimal(Double.toString(v)).setScale(3,
            RoundingMode.HALF_UP);
    }

    /**
     * Metodo que redondea a 2 decimales
     * 
     * @param v
     * @return
     */
    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
