package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name= "TPAYROLLS" ,
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"date","contract_id"})
})
public class Payroll extends BaseEntity {
    private double monthlyBaseSalary;
    private double extraSalary;
    private double productivityEarning;
    private double trienniumEarning;
    private double taxDeduction;
    private double nicDeduction;
    private double grossSalary;

    private LocalDate date;
    @ManyToOne private Contract contract;
    //Para JPA
    Payroll(){}
    public Payroll(Contract contract, LocalDate date) {
	ArgumentChecks.isNotNull(contract, "Payroll:: contract cannot be null");
	ArgumentChecks.isNotNull(date, "Payroll:: date cannot be null");
	LocalDate monthStart = date.withDayOfMonth(1);
	LocalDate contractStartM = contract.getStartDate().withDayOfMonth(1);
	ArgumentChecks.isTrue(!monthStart.isBefore(contractStartM),
			"Payroll:: date is before contract start month");

	this.contract = contract;
	this.date = date;

	computeConcepts();

	Associations.Generates.link(contract, this);
    }

    private void computeConcepts() {
	double baseYear = contract.getAnnualBaseSalary();
	var group = contract.getProfessionalGroup();
	var mechanic = contract.getMechanic();
	monthlyBaseSalary = baseYear / 14.0;
	extraSalary = isExtraMonth(date) ? baseYear / 14.0 : 0.0;
	double monthInterventions = mechanic.getInterventions().stream()
			.filter(iv -> iv.getWorkOrder() != null
					&& iv.getWorkOrder().isInvoiced()
					&& sameYearMonth(iv.getWorkOrder()
							.getDate()
							.toLocalDate(), date))
			.mapToDouble(iv -> iv.getWorkOrder().getAmount()).sum();
	productivityEarning = monthInterventions * group.getProductivityRate();
	int yearsWorked = yearsBetween(contract.getStartDate(), date);
	int trienniums = yearsWorked / 3;
	trienniumEarning = trienniums * group.getTrienniumSalary();

	double taxRate = irpfRateFor(baseYear); // 10k -> 19%, 30k -> 30%
	double earnings = monthlyBaseSalary + extraSalary + productivityEarning
			+ trienniumEarning;
	taxDeduction = earnings * taxRate;
	nicDeduction = (baseYear / 12.0) * 0.05; // STANDARD_NIC
	grossSalary = earnings;
    }

    private static boolean isExtraMonth(LocalDate d) {
	int m = d.getMonthValue();
	return m == 6 || m == 12;
    }

    private static boolean sameYearMonth(LocalDate d1, LocalDate d2) {
	return d1.getYear() == d2.getYear()
			&& d1.getMonthValue() == d2.getMonthValue();
    }



    private static int yearsBetween(LocalDate start, LocalDate end) {
	LocalDate normStart = start.with(TemporalAdjusters.firstDayOfMonth());
	return end.getYear() - normStart.getYear()
			- (end.getDayOfYear() < normStart.getDayOfYear() ? 1
					: 0);
    }

    private double irpfRateFor(double annualBaseSalary) {
	return annualBaseSalary <= 15000 ? 0.19 : 0.30;
    }

    public Double getGrossSalary() {
	return grossSalary;
    }

    public LocalDate getDate() {
	return date;
    }

    public Contract getContract() {
	return contract;
    }

    public double getMonthlyBaseSalary() {
	return monthlyBaseSalary;
    }

    public double getExtraSalary() {
	return extraSalary;
    }

    public double getProductivityEarning() {
	return productivityEarning;
    }

    public double getTrienniumEarning() {
	return trienniumEarning;
    }

    public double getTaxDeduction() {
	return taxDeduction;
    }

    public double getNicDeduction() {
	return nicDeduction;
    }

    public void _setContract(Contract c) {
	this.contract = c;
	
    }


    @Override
    public String toString() {
	return "Payroll [monthlyBaseSalary=" + monthlyBaseSalary
			+ ", extraSalary=" + extraSalary
			+ ", productivityEarning=" + productivityEarning
			+ ", trienniumEarning=" + trienniumEarning
			+ ", taxDeduction=" + taxDeduction + ", nicDeduction="
			+ nicDeduction + ", grossSalary=" + grossSalary
			+ ", date=" + date + ", contract=" + contract + "]";
    }
    
}
