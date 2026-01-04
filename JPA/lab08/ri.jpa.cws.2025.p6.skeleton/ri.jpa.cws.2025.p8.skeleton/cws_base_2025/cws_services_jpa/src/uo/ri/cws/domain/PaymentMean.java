package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="TPAYMENTMEANS")
public abstract class PaymentMean extends BaseEntity {
    // natural attributes
    private double accumulated = 0.0;
    //Para JPA
    PaymentMean(){}
    // accidental attributes
    @ManyToOne private Client client;
    @OneToMany(mappedBy ="paymentMean") private Set<Charge> charges = new HashSet<>();

    public abstract boolean canPay(Double amount);

    public void pay(double importe) {
	this.accumulated += importe;
    }

    public double getAccumulated() {
	return accumulated;
    }


    public Client getClient() {
	return this.client;
    }

    void _setClient(Client client) {
	this.client = client;
    }

    public Set<Charge> getCharges() {
	return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
	return charges;
    }

    @Override
    public String toString() {
	return "PaymentMean [accumulated=" + accumulated + ", client=" + client
			+ ", charges=" + charges + "]";
    }
}
