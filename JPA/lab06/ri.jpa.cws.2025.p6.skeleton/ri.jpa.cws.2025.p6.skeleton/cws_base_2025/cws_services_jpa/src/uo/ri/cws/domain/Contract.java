package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Contract {
    public enum ContractState {
	IN_FORCE, TERMINATED
    }

    private double annualBaseSalary;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType type;
    private ProfessionalGroup group;
    private Mechanic mechanic;
    private Double settlement = 0.0;
    private ContractState state;

    private Set<Payroll> payrolls = new HashSet<Payroll>();

    public Contract(Mechanic mechanic, ContractType type,
		    ProfessionalGroup group, LocalDate signingDate,
		    LocalDate endDate, double annualSalary) {
	ArgumentChecks.isNotNull(mechanic,
			"Contract:: mechanic cannot be null");
	ArgumentChecks.isNotNull(type, "Contract:: type cannot be null");
	ArgumentChecks.isNotNull(group,
			"Contract:: professional group cannot be null");
	ArgumentChecks.isNotNull(signingDate,
			"Contract:: signingDate cannot be null");
	ArgumentChecks.isTrue(annualSalary > 0,
			"Contract:: annualSalary must be > 0");

	LocalDate normalizedStart = signingDate
			.with(TemporalAdjusters.firstDayOfMonth());

	boolean fixedTerm = isFixedTerm(type);

	if (fixedTerm) {
	    ArgumentChecks.isNotNull(endDate,
			    "Contract:: endDate is mandatory for FIXED_TERM");
	    ArgumentChecks.isTrue(!endDate.isBefore(signingDate),
			    "Contract:: endDate must not be before signingDate");
	    this.endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
	} else {
	    this.endDate = null;
	}

	this.mechanic = mechanic;
	this.type = type;
	this.group = group;
	this.startDate = normalizedStart;
	this.annualBaseSalary = annualSalary;
	this.state = ContractState.IN_FORCE;

	Associations.Bind.link(mechanic, this);
	Associations.Defines.link(type, this);
	Associations.Categorizes.link(group, this);

    }
    public Contract(Mechanic mechanic, ContractType type,
		    ProfessionalGroup group, LocalDate signingDate,
		    double annualSalary) {
	this(mechanic, type, group, signingDate, null, annualSalary);
    }

    public Double getAnnualBaseSalary() {
	return annualBaseSalary;
    }

    public LocalDate getStartDate() {
	return startDate;
    }

    public LocalDate getEndDate() {
	return endDate;
    }

    public Double getSettlement() {
	return settlement;
    }

    public ContractType getContractType() {
	return type;
    }

    public ProfessionalGroup getProfessionalGroup() {
	return group;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public boolean isInForce() {
	return this.state == ContractState.IN_FORCE;
    }

    public Set<Payroll> getPayrolls() {
	return new HashSet<Payroll>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
	return this.payrolls;
    }

    private static boolean isFixedTerm(ContractType type) {
	return "FIXED_TERM".equalsIgnoreCase(type.getName());
    }

    void _setMechanic(Mechanic mech) {
	this.mechanic = mech;

    }

    void _setContractType(ContractType type) {
	this.type = type;

    }

    void _setProfessionalGroup(ProfessionalGroup group) {
	this.group = group;

    }

    public void terminate(LocalDate date) {
	ArgumentChecks.isNotNull(date,
			"Contract:: termination date cannot be null");
	if (isTerminated())
	    throw new IllegalStateException("Contract already terminated");
	LocalDate normalizedEnd = date.with(TemporalAdjusters.lastDayOfMonth());

	ArgumentChecks.isTrue(!normalizedEnd.isBefore(startDate),
			"Contract:: end date cannot be before start date");

	int monthsWorked = (int) payrolls.stream()
			.filter(p -> !p.getDate().isAfter(normalizedEnd))
			.count();

	int yearsWorkedForComp = Math.min(monthsWorked / 12, 2);
	if (yearsWorkedForComp <= 0) {
	    settlement = 0.0;
	} else {
	    double dailyGross = annualBaseSalary / 365.0;
	    settlement = yearsWorkedForComp * dailyGross
			    * type.getCompensationDaysPerYear();
	}

	this.endDate = normalizedEnd;
	this.state = ContractState.TERMINATED;
    }

    public boolean isTerminated() {
	return state == ContractState.TERMINATED;
    }
    @Override
    public int hashCode() {
	return Objects.hash(mechanic, startDate);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Contract other = (Contract) obj;
	return Objects.equals(mechanic, other.mechanic)
			&& Objects.equals(startDate, other.startDate);
    }

    @Override
    public String toString() {
	return "Contract [annualBaseSalary=" + annualBaseSalary + ", startDate="
			+ startDate + ", endDate=" + endDate + ", type=" + type
			+ ", group=" + group + ", mechanic=" + mechanic
			+ ", settlement=" + settlement + ", state=" + state
			+ "]";
    }
}
