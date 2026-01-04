package uo.ri.cws.application.service.payroll.crud.commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrolls implements Command<List<PayrollDto>>{
	private static final BigDecimal NIC_PERCENTAGE = BigDecimal.valueOf(0.05);
	private static final int PAYROLL_DIVISOR = 14;
	
	private LocalDate present;
	private LocalDate targetMonth;
	private LocalDate payrollDate;
	
	public GeneratePayrolls(LocalDate p) {
		this.present = p;
		this.targetMonth = this.present.minusMonths(1);
		this.payrollDate = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth());
	}
	
	@Override
	public List<PayrollDto> execute() throws BusinessException {
	    if (payrollsAlreadyGenerated()) {
	        return new ArrayList<>();
	    }

	    List<ContractRecord> contracts = Factories.persistence.forContract()
	        .findInForceOrEndingThisMonth(targetMonth.getMonthValue(), targetMonth.getYear());

	    List<PayrollDto> result = new ArrayList<>();
	    for (ContractRecord contract : contracts) {
	        PayrollDto dto = buildPayroll(contract);
	        Factories.persistence.forPayroll().add(PayrollAssembler.toRecord(dto));
	        result.add(dto);
	    }

	    return result;
	}


	private boolean payrollsAlreadyGenerated() {
	    List<PayrollRecord> payrolls = Factories.persistence.forPayroll()
	        .findInDates(targetMonth.getMonthValue(), targetMonth.getYear());
	    return !payrolls.isEmpty();
	}

	
	private PayrollDto buildPayroll(ContractRecord contract) throws BusinessException {
        ProfessionalGroupRecord group = Factories.persistence.forProfessionalGroup()
            .findById(contract.professionalGroupId)
            .orElseThrow(() -> new BusinessException("Grupo profesional no encontrado"));

        BigDecimal monthlyWage = BigDecimal.valueOf(contract.annualBaseWage)
            .divide(BigDecimal.valueOf(PAYROLL_DIVISOR), 2, RoundingMode.HALF_UP);

        BigDecimal bonus = (targetMonth.getMonth().equals(Month.JUNE) || targetMonth.getMonth().equals(Month.DECEMBER))
            ? monthlyWage
            : BigDecimal.ZERO;

        BigDecimal productivityBonus = calculateProductivityBonus(contract, group);
        BigDecimal trienniumPayment = calculateTrienniumPayment(contract, group);

        BigDecimal grossSalary = monthlyWage.add(bonus).add(productivityBonus).add(trienniumPayment)
            .setScale(2, RoundingMode.HALF_UP);

        BigDecimal incomeTax = calculateIncomeTax(contract.annualBaseWage, grossSalary);
        BigDecimal nic = BigDecimal.valueOf(contract.annualBaseWage)
            .divide(BigDecimal.valueOf(12), 3, RoundingMode.HALF_UP)
            .multiply(NIC_PERCENTAGE)
            .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDeductions = incomeTax.add(nic).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netSalary = grossSalary.subtract(totalDeductions).setScale(2, RoundingMode.HALF_UP);

        PayrollRecord record = new PayrollRecord();
        record.id = UUID.randomUUID().toString();
        record.version = 1L;
        record.date = payrollDate;
        record.contractId = contract.id;
        record.monthlyWage = monthlyWage.doubleValue();
        record.bonus = bonus.doubleValue();
        record.productivityBonus = productivityBonus.doubleValue();
        record.trienniumPayment = trienniumPayment.doubleValue();
        record.incomeTax = incomeTax.doubleValue();
        record.nic = nic.doubleValue();
        record.netWage = netSalary.doubleValue();
        LocalDateTime now = LocalDateTime.now();
        record.createdAt = now;
        record.updatedAt = now;

        return PayrollAssembler.toDto(record);

    }

	private BigDecimal calculateProductivityBonus(ContractRecord contract, ProfessionalGroupRecord group) {
	    List<WorkOrderRecord> workOrders = Factories.persistence.forWorkOrder().findByInterventionMechanic(contract.mechanicId);
	    BigDecimal total = BigDecimal.ZERO;

	    LocalDate start = targetMonth.withDayOfMonth(1);
	    LocalDate end = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth());

	    for (WorkOrderRecord wo : workOrders) {
	        LocalDate woDate = wo.date.toLocalDate();
	        if (!woDate.isBefore(start) && !woDate.isAfter(end)
	            && "INVOICED".equalsIgnoreCase(wo.state)) {
	            total = total.add(BigDecimal.valueOf(wo.amount));
	        }
	    }

	    return total.multiply(BigDecimal.valueOf(group.productivityRate))
	                .setScale(3, RoundingMode.HALF_UP);
	}



    private BigDecimal calculateTrienniumPayment(ContractRecord contract, ProfessionalGroupRecord group) {
        long years = ChronoUnit.YEARS.between(contract.startDate, targetMonth);
        long trienniums = years / 3;
        return BigDecimal.valueOf(group.trienniumPayment)
            .multiply(BigDecimal.valueOf(trienniums))
            .setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateIncomeTax(double annualBaseWage, BigDecimal grossSalary) {
        double rate;
        if (annualBaseWage <= 12450) rate = 19;
        else if (annualBaseWage <= 20200) rate = 24;
        else if (annualBaseWage <= 35200) rate = 30;
        else if (annualBaseWage <= 60000) rate = 37;
        else if (annualBaseWage <= 300000) rate = 45;
        else rate = 47;

        return grossSalary.multiply(BigDecimal.valueOf(rate / 100))
            .setScale(2, RoundingMode.HALF_UP);
    }

}
