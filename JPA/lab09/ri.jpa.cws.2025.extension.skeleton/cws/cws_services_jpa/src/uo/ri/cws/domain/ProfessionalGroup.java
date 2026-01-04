package uo.ri.cws.domain;

import java.util.HashSet; 
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup extends BaseEntity {
    private String name;
    private double trienniumPayment;
    private Double productivityRate;
    private Set<Contract> contracts = new HashSet<>();

    // Para JPA
    ProfessionalGroup() {
    }

    public ProfessionalGroup(
			     String name, double trienniumPayment,
			     double productivityRate) {
	ArgumentChecks.isNotEmpty(name,
				  "ProfessionalGroup:: receiving invalid name");
	ArgumentChecks.isTrue(trienniumPayment >= 0,
			      "ProfessionalGroup:: reciving invalid triennium salary");
	ArgumentChecks.isTrue(productivityRate >= 0,
			      "ProfessionalGroup:: reciving invalid productivity plus");
	this.name = name;
	this.trienniumPayment = trienniumPayment;
	this.productivityRate = productivityRate;
    }

    public String
	   getName() {
	return name;
    }

    public double
	   getTrienniumPayment() {
	return trienniumPayment;
    }

    public double
	   getProductivityRate() {
	return productivityRate;
    }

    public Set<Contract>
	   getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

}
