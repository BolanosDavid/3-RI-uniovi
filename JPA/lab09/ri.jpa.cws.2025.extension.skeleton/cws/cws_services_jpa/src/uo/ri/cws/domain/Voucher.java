package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Voucher extends PaymentMean {
    private String code;
    private double available = 0.0;
    private String description;

    // Para JPA
    Voucher() {
    }

    // Full constructor
    public Voucher(
		   String code, String description, double available) {
	ArgumentChecks.isNotEmpty(code,
				  "Voucher:: not valid code");
	ArgumentChecks.isTrue(available > 0,
			      "Voucher:: not valid available");
	ArgumentChecks.isNotEmpty(description,
				  "Voucher:: not valid description");
	this.code = code;
	this.available = available;
	this.description = description;
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void
	   pay(double amount) {
	super.pay(amount);
	this.available -= amount;
    }

    /**
     * A voucher can pay if it has enough available to pay the amount
     */
    @Override
    public boolean canPay(Double amount) {
	ArgumentChecks.isTrue(amount != null && amount >= 0,
			      "Voucher:: invalid amount");
	return this.available >= amount;

    }


    public String
	   getCode() {
	return code;
    }

    public String
	   getDescription() {
	return description;

    }

    public double
	   getAvailable() {
	return available;
    }

}
