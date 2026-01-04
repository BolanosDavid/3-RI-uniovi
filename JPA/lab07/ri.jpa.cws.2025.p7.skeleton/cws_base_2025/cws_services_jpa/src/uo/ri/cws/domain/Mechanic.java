package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name= "TMECHANIC")
public class Mechanic extends BaseEntity{
    // natural attributes
    @Column (unique =true )private String nif;
    private String surname;
    private String name;

    // accidental attributes
    @OneToMany(mappedBy = "mechanic") private Set<WorkOrder> assigned = new HashSet<WorkOrder>();
    @OneToMany(mappedBy = "mechanic") private Set<Intervention> interventions = new HashSet<Intervention>();
    @OneToMany(mappedBy = "mechanic") private Set<Contract> contracts = new HashSet<Contract>();
    //Para JPA
    public Mechanic() {}
    //Full contructor
    public Mechanic(String nif, String surname, String name) {
	ArgumentChecks.isNotBlank(nif, "Mechanic:: not valid nif");
	ArgumentChecks.isNotBlank(surname, "Mechanic:: not valid surname");
	ArgumentChecks.isNotBlank(name, "Mechanic:: not valid name");

	this.nif = nif;
	this.surname = surname;
	this.name = name;

    }

    public Mechanic(String nif) {
	this(nif,"nosurname","noname");
    }
    public Set<WorkOrder> getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public Set<Contract> getContracts() {
	return new HashSet<Contract>(contracts);
    }

    Set<Contract> _getContracts() {
	return this.contracts;
    }

    public String getNif() {
	return nif;
    }

    public String getSurname() {
	return surname;
    }

    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
			+ name + ", assigned=" + assigned + ", interventions="
			+ interventions + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(nif);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Mechanic other = (Mechanic) obj;
	return Objects.equals(nif, other.nif);
    }

    public Optional<Contract> getContractInForce() {
	return contracts.stream().filter(Contract::isInForce).findFirst();
    }

}
