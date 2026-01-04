package uo.ri.cws.domain;

import java.util.HashSet; 
import java.util.Optional;
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic extends BaseEntity {
    // natural attributes
    private String nif;
    private String surname;
    private String name;

    // accidental attributes
    private Set<WorkOrder> assigned = new HashSet<WorkOrder>();
    private Set<Intervention> interventions = new HashSet<Intervention>();
    private Set<Contract> contracts = new HashSet<Contract>();

    // Para JPA
    Mechanic() {
    }

    // Full contructor
    public Mechanic(
		    String nif, String surname, String name) {
	checkParams(nif,
		    surname,
		    name);

	this.nif = nif;
	this.surname = surname;
	this.name = name;

    }

    public Mechanic(
		    String nif) {
	this(nif, "nosurname", "noname");
    }

    public Set<WorkOrder>
	   getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention>
	   getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public Set<Contract>
	   getContracts() {
	return new HashSet<Contract>(contracts);
    }

    Set<Contract> _getContracts() {
	return this.contracts;
    }

    public String
	   getNif() {
	return nif;
    }

    public String
	   getSurname() {
	return surname;
    }

    public String
	   getName() {
	return name;
    }

    @Override
    public String
	   toString() {
	return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
			+ name + "]";
    }

    public Optional<Contract>
	   getContractInForce() {
	return contracts.stream()
			.filter(Contract::isInForce)
			.findFirst();
    }

    public void
	   setName(String name) {
	this.name = name;
    }

    public void
	   setSurname(String surname) {
	this.surname = surname;

    }

    private void
	    checkParams(String nif,
			String surname,
			String name) {
	ArgumentChecks.isNotEmpty(nif,
				  "Mechanic:: not valid nif");
	ArgumentChecks.isNotEmpty(surname,
				  "Mechanic:: not valid surname");
	ArgumentChecks.isNotEmpty(name,
				  "Mechanic:: not valid name");
	ArgumentChecks.isNotBlank(nif,
				  "Mechanic:: not valid nif");
	ArgumentChecks.isNotBlank(surname,
				  "Mechanic:: not valid surname");
	ArgumentChecks.isNotBlank(name,
				  "Mechanic:: not valid name");
    }

}
