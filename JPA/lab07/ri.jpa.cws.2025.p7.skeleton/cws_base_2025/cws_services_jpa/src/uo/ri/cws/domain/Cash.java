package uo.ri.cws.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name ="TCASHES")
public class Cash extends PaymentMean {

    public Cash() {}
    public Cash(Client client) {
	_setClient(client);
	Associations.Holds.link(this, client);

    }

    /**
     * A cash can always pay
     */
    @Override
    public boolean canPay(Double amount) {
	return true;
    }



}
