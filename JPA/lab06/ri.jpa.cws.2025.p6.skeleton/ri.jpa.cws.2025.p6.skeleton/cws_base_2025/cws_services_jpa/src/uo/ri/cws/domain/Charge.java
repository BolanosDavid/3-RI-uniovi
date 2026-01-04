package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Charge {
    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    private Invoice invoice;
    private PaymentMean paymentMean;

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
	ArgumentChecks.isNotNull(invoice, "Charge:: receiving null invoice");
	ArgumentChecks.isNotNull(paymentMean,"Charge:: receiving null paymentMean");
	ArgumentChecks.isTrue(amount > 0, "Charge:: amount must be > 0");   
	ArgumentChecks.isTrue(invoice.isNotSettled(), "Charge:: invoice already settled");
	StateChecks.isTrue(paymentMean.canPay(amount) ,"Charge:: payment mean cannot pay this amount");

	
	// store the amount
	this.amount = amount;
	// link invoice, this and paymentMean
	Associations.Settles.link(invoice, this, paymentMean);
	paymentMean.pay(amount);
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     * 
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
	// asserts the invoice is not in PAID status
	if(!invoice.isNotSettled()) throw new IllegalArgumentException("Charge:: invoice must be not settled");
	// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
	paymentMean.pay(-amount);
	// unlinks invoice, this and paymentMean
	Associations.Settles.unlink(this);
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;

    }

    void _setPaymentMean(PaymentMean mp) {
	this.paymentMean = mp;

    }

    public Invoice getInvoice() {
	return this.invoice;
    }

    public PaymentMean getPaymentMean() {
	return paymentMean;
    }

    public double getAmount() {
	return amount;
    }

}
