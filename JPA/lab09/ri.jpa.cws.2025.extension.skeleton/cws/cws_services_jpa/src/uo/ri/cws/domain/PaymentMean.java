package uo.ri.cws.domain;

import java.util.HashSet;  
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;

public abstract class PaymentMean extends BaseEntity {
    // natural attributes
    private double accumulated = 0.0;

    // Para JPA
    PaymentMean() {
    }

    // accidental attributes
    private Client client;
    private Set<Charge> charges = new HashSet<>();

    public abstract boolean
	   canPay(Double amount);

    public void
	   pay(double importe) {
	this.accumulated += importe;
    }

    public double
	   getAccumulated() {
	return accumulated;
    }

    public Client
	   getClient() {
	return this.client;
    }

    void _setClient(Client client) {
	this.client = client;
    }

    public Set<Charge>
	   getCharges() {
	return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
	return charges;
    }
}
