package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class Voucher extends PaymentMean {
    private String code;
    private double available = 0.0;
    private String description;

    // Full constructor
    public Voucher(String code, String description, double available) {
	ArgumentChecks.isNotEmpty(code, "Voucher:: not valid code");
	ArgumentChecks.isTrue(available > 0, "Voucher:: not valid available");
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
    public void pay(double amount) {
	super.pay(amount);
	this.available -= amount;
    }

    /**
     * A voucher can pay if it has enough available to pay the amount
     */
    @Override
    public boolean canPay(Double amount) {
	ArgumentChecks.isTrue(amount != null && amount > 0, "Voucher:: invalid amount");
	return this.available >= amount;

    }

    @Override
    public String toString() {
	return "Voucher [code=" + code + ", available=" + available
			+ ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Voucher other = (Voucher) obj;
	return Objects.equals(code, other.code);
    }

}
