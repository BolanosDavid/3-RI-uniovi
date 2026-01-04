package uo.ri.cws.domain;

import java.util.HashSet;  
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
 

public class ContractType extends BaseEntity {
    private String name;
    private Double compensationDaysPerYear;
    private Set<Contract> contracts = new HashSet<Contract>();

    // Para JPA
    ContractType() {
    }

    public ContractType(
			String name, double compensationDaysPerYear) {
	ArgumentChecks.isNotEmpty(name,
				  "ContractType:: receiving invalid name");
	ArgumentChecks.isTrue(compensationDaysPerYear > 0,
		  "ContractType:: receiving invalid compensation days per year");
	this.name = name;
	this.compensationDaysPerYear = compensationDaysPerYear;
    }

    public String
	   getName() {
	return this.name;
    }

    public Double
	   getCompensationDaysPerYear() {
	return this.compensationDaysPerYear;
    }

    public Set<Contract>
	   getContracts() {
	return new HashSet<Contract>(contracts);
    }

    Set<Contract> _getContracts() {
	return this.contracts;

    }

    public void
	   setCompensationDaysPerYear(double compensationDaysPerYear) {
	this.compensationDaysPerYear = compensationDaysPerYear;
    }


}
