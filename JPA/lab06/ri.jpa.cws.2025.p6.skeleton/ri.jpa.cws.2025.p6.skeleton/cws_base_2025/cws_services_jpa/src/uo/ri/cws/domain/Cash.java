package uo.ri.cws.domain;

import java.util.Objects;

public class Cash extends PaymentMean {

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

    @Override
    public int hashCode() {
	return Objects.hash(getClass(), getClient());
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Cash c = (Cash) o;
	return Objects.equals(getClient(), c.getClient());
    }

}
