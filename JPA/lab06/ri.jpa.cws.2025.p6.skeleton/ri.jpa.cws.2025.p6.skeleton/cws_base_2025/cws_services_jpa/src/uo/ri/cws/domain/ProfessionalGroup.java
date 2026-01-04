package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup {
    private String name;
    private double trienniumSalary;
    private Double productivityPlus;
    private Set<Contract> contracts = new HashSet<>();

    public ProfessionalGroup(String name, double trienniumSalary,
		    double productivityPlus) {
	ArgumentChecks.isNotEmpty(name,
			"ProfessionalGroup:: receiving invalid name");
	ArgumentChecks.isTrue(trienniumSalary >= 0,
			"ProfessionalGroup:: reciving invalid triennium salary");
	ArgumentChecks.isTrue(productivityPlus >= 0,
			"ProfessionalGroup:: reciving invalid productivity plus");
	this.name = name;
	this.trienniumSalary = trienniumSalary;
	this.productivityPlus = productivityPlus;
    }

    public String getName() {
	return name;
    }

    public double getTrienniumSalary() {
	return trienniumSalary;
    }

    public double getProductivityRate() {
	return productivityPlus;
    }

    public Set<Contract> getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	ProfessionalGroup other = (ProfessionalGroup) obj;
	return Objects.equals(name, other.name);
    }
}
