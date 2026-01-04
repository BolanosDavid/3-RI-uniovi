package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Contract extends BaseEntity {
    public enum ContractState {
        IN_FORCE, TERMINATED
    }

    private double annualBaseSalary;
    private LocalDate startDate;
    private LocalDate endDate;
    private double taxRate;
    private ContractType type;
    private ProfessionalGroup group;
    private Mechanic mechanic;
    private Double settlement = 0.0;
    private ContractState state;
    private Set<Payroll> payrolls = new HashSet<Payroll>();

    // Para JPA
    Contract() {
    }

    public Contract(
                    Mechanic mechanic, ContractType type,
                    ProfessionalGroup group, LocalDate signingDate,
                    LocalDate endDate, double annualSalary) {
        ArgumentChecks.isNotNull(mechanic,
                                 "Contract:: mechanic cannot be null");
        ArgumentChecks.isNotNull(type,
                                 "Contract:: type cannot be null");
        ArgumentChecks.isNotNull(group,
                                 "Contract:: professional group cannot be null");
        ArgumentChecks.isNotNull(signingDate,
                                 "Contract:: signingDate cannot be null");
        ArgumentChecks.isTrue(annualSalary > 0,
                              "Contract:: annualSalary must be > 0");

        LocalDate normalizedStart = signingDate.with(
                                              TemporalAdjusters.firstDayOfMonth());

        checkFixedTerm(type,
                       signingDate,
                       endDate);
        this.mechanic = mechanic;
        this.type = type;
        this.group = group;
        this.startDate = normalizedStart;
        this.annualBaseSalary = annualSalary;
        this.state = ContractState.IN_FORCE;

        Associations.Bind.link(mechanic,
                               this);
        Associations.Defines.link(type,
                                  this);
        Associations.Categorizes.link(group,
                                      this);

    }

    public Contract(
                    Mechanic mechanic, ContractType type,
                    ProfessionalGroup group, LocalDate signingDate,
                    double annualSalary) {
        this(mechanic, type, group, signingDate, null, annualSalary);
    }

    public Double
           getAnnualBaseSalary() {
        return annualBaseSalary;
    }

    public LocalDate
           getStartDate() {
        return startDate;
    }

    public LocalDate
           getEndDate() {
        return endDate;
    }

    public Double
           getSettlement() {
        return settlement;
    }

    public ContractType
           getContractType() {
        return type;
    }

    public ProfessionalGroup
           getProfessionalGroup() {
        return group;
    }

    public Mechanic
           getMechanic() {
        return mechanic;
    }

    public boolean
           isInForce() {
        return this.state == ContractState.IN_FORCE;
    }

    public Set<Payroll>
           getPayrolls() {
        return new HashSet<Payroll>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
        return this.payrolls;
    }

    private static boolean
            isFixedTerm(ContractType type) {
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

    public void
           terminate(LocalDate date) {
        validateTerminationDate(date);
        LocalDate normalizedEnd = normalizeEndDate(date);
        validateEndNotBeforeStart(normalizedEnd);

        int monthsWorked = calculateMonthsWorkedUntil(normalizedEnd);
        settlement = calculateSettlement(monthsWorked);

        this.endDate = normalizedEnd;
        this.state = ContractState.TERMINATED;
    }

    private void
            validateTerminationDate(LocalDate date) {
        ArgumentChecks.isNotNull(date,
                                 "Contract:: termination date cannot be null");
        if (isTerminated()) {
            throw new IllegalStateException("Contract already terminated");
        }
    }

    private LocalDate
            normalizeEndDate(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    private void
            validateEndNotBeforeStart(LocalDate normalizedEnd) {
        ArgumentChecks.isTrue(!normalizedEnd.isBefore(startDate),
                              "Contract:: end date cannot be before start date");
    }

    private int
            calculateMonthsWorkedUntil(LocalDate normalizedEnd) {
        return (int) payrolls.stream()
                             .filter(p -> !p.getDate()
                                            .isAfter(normalizedEnd))
                             .count();
    }

    private double
            calculateSettlement(int monthsWorked) {
        int yearsWorkedForComp = monthsWorked / 12;
        if (yearsWorkedForComp < 1) {
            return 0.0;
        }
        double dailyGross = annualBaseSalary / 365.0;
        return yearsWorkedForComp * dailyGross
                        * type.getCompensationDaysPerYear();
    }

    public boolean
           isTerminated() {
        return state == ContractState.TERMINATED;
    }

    public double
           getTaxRate() {
        if (annualBaseSalary <= 12450) {
            this.taxRate = 0.19;
        } else if (annualBaseSalary <= 20200) {
            this.taxRate = 0.24;
        } else if (annualBaseSalary <= 35200) {
            this.taxRate = 0.30;
        } else if (annualBaseSalary <= 60000) {
            this.taxRate = 0.37;
        } else if (annualBaseSalary <= 300000) {
            this.taxRate = 0.45;
        } else {
            this.taxRate = 0.47;
        }
        return taxRate;
    }

    private void
            checkFixedTerm(ContractType type,
                           LocalDate signingDate,
                           LocalDate endDate) {
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
    }

    public ContractState
           getState() {
        return this.state;
    }

}
