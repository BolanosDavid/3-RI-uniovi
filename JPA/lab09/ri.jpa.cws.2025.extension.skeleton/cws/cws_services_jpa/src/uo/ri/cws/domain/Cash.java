package uo.ri.cws.domain;


public class Cash extends PaymentMean {

    Cash() {
    }

    public Cash(
		Client client) {
	_setClient(client);
	Associations.Holds.link(this,
				client);

    }

    /**
     * A cash can always pay
     */
    @Override
    public boolean
	   canPay(Double amount) {
	return true;
    }

}
