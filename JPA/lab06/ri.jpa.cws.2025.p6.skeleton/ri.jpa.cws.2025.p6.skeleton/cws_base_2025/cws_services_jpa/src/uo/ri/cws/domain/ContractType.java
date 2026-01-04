package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ContractType {
    private String name;
    private Double compensationDaysPerYear;
    private Set<Contract> contracts = new HashSet<Contract>();

    public ContractType(String name, double compensationDaysPerYear) {
	ArgumentChecks.isNotEmpty(name,
			"ContractType:: receiving invalid name");
	ArgumentChecks.isTrue(compensationDaysPerYear > 0,
			"ContractType:: receiving invalid compensation days per year");
	this.name = name;
	this.compensationDaysPerYear = compensationDaysPerYear;
    }

    public String getName() {
	return this.name;
    }

    public Double getCompensationDaysPerYear() {
	return this.compensationDaysPerYear;
    }

    public Set<Contract> getContracts() {
	return new HashSet<Contract>(contracts);
    }

    Set<Contract> _getContracts() {
	return this.contracts;

    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ContractType other = (ContractType) obj;
	return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
	return "ContractType [name=" + name + ", compensationDaysPerYear="
			+ compensationDaysPerYear + "]";
    }

}
