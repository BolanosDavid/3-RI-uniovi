package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name="TCONTRACTTYPES")
public class ContractType extends BaseEntity{
    @Column(unique=true) private String name;
    private Double compensationDaysPerYear;
    @OneToMany(mappedBy="type") private Set<Contract> contracts = new HashSet<Contract>();
    //Para JPA
    ContractType(){}
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
    public String toString() {
	return "ContractType [name=" + name + ", compensationDaysPerYear="
			+ compensationDaysPerYear + "]";
    }

}
