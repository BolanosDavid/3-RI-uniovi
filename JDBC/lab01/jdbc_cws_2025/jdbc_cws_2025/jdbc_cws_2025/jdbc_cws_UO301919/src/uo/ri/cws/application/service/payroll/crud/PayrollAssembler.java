package uo.ri.cws.application.service.payroll.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;

public class PayrollAssembler {

	public static PayrollRecord toRecord(PayrollDto dto) {
        PayrollRecord r = new PayrollRecord();
        r.id = dto.id;
        r.version = dto.version;
        r.contractId = dto.contractId;
        r.date = dto.date;

        r.monthlyWage = dto.baseSalary;
        r.bonus = dto.extraSalary;
        r.productivityBonus = dto.productivityEarning;
        r.trienniumPayment = dto.trienniumEarning;

        r.incomeTax = dto.taxDeduction;
        r.nic = dto.nicDeduction;

        r.netWage = dto.netSalary;

        return r;
    }

    public static PayrollDto toDto(PayrollRecord record) {
        PayrollDto dto = new PayrollDto();
        dto.id = record.id;
        dto.version = record.version;
        dto.contractId = record.contractId;
        dto.date = record.date;

        dto.baseSalary = record.monthlyWage;
        dto.extraSalary = record.bonus;
        dto.productivityEarning = record.productivityBonus;
        dto.trienniumEarning = record.trienniumPayment;

        dto.taxDeduction = record.incomeTax;
        dto.nicDeduction = record.nic;

        dto.grossSalary = record.monthlyWage + record.bonus + record.productivityBonus + record.trienniumPayment;
        dto.totalDeductions = record.incomeTax + record.nic;
        dto.netSalary = dto.grossSalary - dto.totalDeductions;

        return dto;
    }
    
    public static List<PayrollDto> toDtoList(List<PayrollRecord> records) {
    	List<PayrollDto> result = new ArrayList<>();
        for (PayrollRecord record : records) {
            result.add(toDto(record));
        }
        return result;
    }

	public static Optional<PayrollDto> toDto(Optional<PayrollRecord> pr) {
		Optional<PayrollDto> result = pr.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toDto(pr.get()));
		return result;
	}

	public static PayrollSummaryDto toSummaryDto(PayrollDto dto) {
		PayrollSummaryDto summary = new PayrollSummaryDto();
		summary.id = dto.id;
		summary.date = dto.date;
		summary.netSalary = dto.netSalary;
		return summary;
	}


}
