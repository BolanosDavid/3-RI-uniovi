package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;

public class PayrollAssembler {

    public static Optional<PayrollDto> toDto(Optional<PayrollRecord> p) {

        PayrollRecord r = p.get();
        PayrollDto d = new PayrollDto();
        if (p.isPresent()) {
            d.id = r.id;
            d.version = r.version;
            d.contractId = r.contractId;
            d.date = r.date;
            d.baseSalary = r.baseSalary;
            d.extraSalary = r.extraSalary;
            d.productivityEarning = r.productivityEarning;
            d.trienniumEarning = r.trienniumEarning;
            d.taxDeduction = r.taxDeduction;
            d.nicDeduction = r.nicDeduction;
            return Optional.ofNullable(d);
        }
        return Optional.empty();
    }

    public static PayrollSummaryDto toPayrollSummaryDto(PayrollRecord r) {
        PayrollSummaryDto s = new PayrollSummaryDto();
        s.id = r.id;
        s.date = r.date;
        return s;
    }

    public static PayrollRecord toRecord(ContractRecord c,
                                         LocalDate end,
                                         double baseMonthly,
                                         double extra,
                                         double triennium,
                                         double productivity,
                                         double nic,
                                         double tax) {
        PayrollRecord r = new PayrollRecord();
        r.id = UUID.randomUUID()
            .toString();
        r.contractId = c.id;
        r.date = end;
        r.baseSalary = baseMonthly;
        r.extraSalary = extra;
        r.trienniumEarning = triennium;
        r.productivityEarning = productivity;
        r.nicDeduction = nic;
        r.taxDeduction = tax;
        r.version = 1L;
        return r;
    }

    public static PayrollRecord toRecord(String payrollId,
                                         Calculated c,
                                         String contractId,
                                         LocalDate date) {
        PayrollRecord r = new PayrollRecord();
        r.id = payrollId;
        r.baseSalary = c.baseSalary;
        r.extraSalary = c.extraSalary;
        r.trienniumEarning = c.trienniumEarning;
        r.productivityEarning = c.productivityEarning;
        r.taxDeduction = c.taxDeduction;
        r.nicDeduction = c.nicDeduction;
        r.contractId = contractId;
        r.date = date;
        r.version = 1L;
        return r;
    }

    public static PayrollDto toDto(String payrollId,
                                   Calculated c,
                                   String contractId,
                                   LocalDate date) {
        PayrollDto dto = new PayrollDto();
        dto.id = payrollId;
        dto.baseSalary = c.baseSalary;
        dto.extraSalary = c.extraSalary;
        dto.trienniumEarning = c.trienniumEarning;
        dto.productivityEarning = c.productivityEarning;
        dto.taxDeduction = c.taxDeduction;
        dto.nicDeduction = c.nicDeduction;
        dto.grossSalary = c.grossSalary;
        dto.totalDeductions = c.totalDeductions;
        dto.netSalary = c.netSalary;
        dto.contractId = contractId;
        dto.date = date;
        dto.version = 1L;
        return dto;
    }

}
