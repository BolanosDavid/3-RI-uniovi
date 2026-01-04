package uo.ri.cws.domain;

import java.math.BigDecimal;  
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Payroll extends BaseEntity {

    private double monthlyBaseSalary;
    private double extraSalary;
    private double productivityEarning;
    private double trienniumEarning;
    private double taxDeduction;
    private double nicDeduction;


    private LocalDate date;
    private Contract contract;

    // Para JPA
    Payroll() {
    }

    public Payroll(
		   double monthlyBaseSalary, double extraSalary,
		   double productivityEarning, double trienniumEarning,
		   double taxDeduction, double nicDeduction,LocalDate date,
		   Contract contract) {
	ArgumentChecks.isTrue(monthlyBaseSalary >= 0,
			      "Payroll monthlyBaseSalary cannot be under 0");
	ArgumentChecks.isTrue(extraSalary >= 0,
			      "Payroll extraSalary cannot be under 0");
	ArgumentChecks.isTrue(productivityEarning >= 0,
			      "Payroll productivityEarning cannot be under 0");
	ArgumentChecks.isTrue(trienniumEarning >= 0,
			      "Payroll trienniumEarning cannot be under 0");
	ArgumentChecks.isTrue(taxDeduction >= 0,
			      "Payroll taxDeduction cannot be under 0");
	ArgumentChecks.isTrue(nicDeduction >= 0,
			      "Payroll nicDeduction cannot be under 0");
	ArgumentChecks.isNotNull(contract,
				 "Payroll:: contract cannot be null");
	ArgumentChecks.isNotNull(date,
				 "Payroll:: date cannot be null");

	LocalDate monthStart = date.withDayOfMonth(1);
	LocalDate contractStartM = contract.getStartDate()
					   .withDayOfMonth(1);
	ArgumentChecks.isTrue(!monthStart.isBefore(contractStartM),
			      "Payroll:: date is before contract start month");
	this.contract = contract;
	this.date = date;
	this.monthlyBaseSalary = monthlyBaseSalary;
	this.extraSalary = extraSalary;
	this.productivityEarning = productivityEarning;
	this.trienniumEarning = trienniumEarning;
	this.taxDeduction = taxDeduction;
	this.nicDeduction = nicDeduction;

	this.date = date;
	this.contract = contract;
	calculaCampos();

	Associations.Generates.link(contract,
				    this);
    }

    public Payroll(Contract contract, LocalDate date) {
	this(0, 0, 0, 0, 0, 0, date, contract);
    }



    private static boolean
	    isExtraMonth(LocalDate d) {
	int m = d.getMonthValue();
	return m == 6 || m == 12;
    }

    private static boolean
	    sameYearMonth(LocalDate d1,
			  LocalDate d2) {
	return d1.getYear() == d2.getYear()
			&& d1.getMonthValue() == d2.getMonthValue();
    }

    private static int
	    yearsBetween(LocalDate start,
			 LocalDate end) {
	LocalDate normStart = start.with(TemporalAdjusters.firstDayOfMonth());
	return end.getYear() - normStart.getYear()
			- (end.getDayOfYear() < normStart.getDayOfYear() ? 1
					: 0);
    }

    private BigDecimal
	    r3BD(double v) {
	return new BigDecimal(Double.toString(v)).setScale(3,
							   RoundingMode.HALF_UP);
    }
    private BigDecimal r2BD(double v) {
	return new BigDecimal(Double.toString(v)).setScale(2,
						   RoundingMode.HALF_UP);
    }


    public LocalDate
	   getDate() {
	return date;
    }

    public Contract
	   getContract() {
	return contract;
    }

    public double
	   getMonthlyBaseSalary() {
	return monthlyBaseSalary;
    }

    public double
	   getExtraSalary() {
	return extraSalary;
    }

    public double
	   getProductivityEarning() {
	return productivityEarning;
    }

    public double
	   getTrienniumEarning() {
	return trienniumEarning;
    }

    public double
	   getTaxDeduction() {
	return taxDeduction;
    }

    public double
	   getNicDeduction() {
	return nicDeduction;
    }

    public double
	   getTotalDeductions() {
	return r3BD(taxDeduction + nicDeduction).doubleValue();
    }

    public double
	   getNetSalary() {
	return r2BD(getGrossSalary() - getTotalDeductions()).doubleValue();
    }

    public Double
	   getGrossSalary() {
	return r3BD(monthlyBaseSalary +trienniumEarning +productivityEarning 
	            					      + extraSalary)
			.doubleValue();
    }
    public void
	   _setContract(Contract c) {
	this.contract = c;
    }

    private void calculaCampos() {
	    double baseYear = contract.getAnnualBaseSalary();
	    
	    monthlyBaseSalary = calcularSalarioBaseMensual(baseYear);
	    extraSalary = calcularPagaExtra(baseYear);
	    productivityEarning = calcularProductividad();
	    trienniumEarning = calcularTrienios();
	    
	    double totalEarnings = calcularPercepcionesTotales();
	    
	    taxDeduction = calcularRetencionFiscal(totalEarnings);
	    nicDeduction = calcularCotizacionSeguridadSocial(baseYear);
	}

	private double calcularSalarioBaseMensual(double baseYear) {
	    return baseYear / 14.0;
	}

	private double calcularPagaExtra(double baseYear) {
	    return isExtraMonth(date) ? baseYear / 14.0 : 0.0;
	}

	private double calcularProductividad() {
	    double totalInterventions = obtenerImporteIntervencionesDelMes();
	    ProfessionalGroup group = contract.getProfessionalGroup();
	    return totalInterventions * group.getProductivityRate();
	}

	private double obtenerImporteIntervencionesDelMes() {
	    Mechanic mechanic = contract.getMechanic();
	    
	    return mechanic.getInterventions()
	        .stream()
	        .filter(this::esIntervencionFacturadaDelMes)
	        .mapToDouble(iv -> iv.getWorkOrder().getAmount())
	        .sum();
	}

	private boolean esIntervencionFacturadaDelMes(Intervention intervention) {
	    WorkOrder workOrder = intervention.getWorkOrder();
	    
	    if (workOrder == null || !workOrder.isInvoiced()) {
	        return false;
	    }
	    
	    LocalDate workOrderDate = workOrder.getDate().toLocalDate();
	    return sameYearMonth(workOrderDate, date);
	}

	private double calcularTrienios() {
	    int yearsWorked = yearsBetween(contract.getStartDate(), date);
	    int trienniums = Math.max(0, yearsWorked / 3);
	    
	    ProfessionalGroup group = contract.getProfessionalGroup();
	    return trienniums * group.getTrienniumPayment();
	}

	private double calcularPercepcionesTotales() {
	    return monthlyBaseSalary + extraSalary 
			    + productivityEarning + trienniumEarning;
	}

	private double calcularRetencionFiscal(double earnings) {
	    double taxRate = contract.getTaxRate();
	    return earnings * taxRate;
	}

	private double calcularCotizacionSeguridadSocial(double baseYear) {
	    return (baseYear / 12.0) * 0.05;
	}

}
