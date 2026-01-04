package uo.ri.cws.domain;

import java.time.LocalDate;  
import uo.ri.util.assertion.ArgumentChecks;


public class CreditCard extends PaymentMean {
    private String number;
    private String type;
    private LocalDate validThru;

    CreditCard() {
    }

    public CreditCard(
		      String number, String type, LocalDate validThru) {
	ArgumentChecks.isNotEmpty(number,
				  "CreditCard:: reciving invalid name");
	ArgumentChecks.isNotEmpty(type,
				  "CreditCard:: reciving invalid type");
	ArgumentChecks.isNotNull(validThru,
				 "CreditCard:: reciving invalid valid date");

	this.number = number;
	this.type = type;
	this.validThru = validThru;
    }

    public String
	   getNumber() {
	return number;
    }

    public String
	   getType() {
	return type;
    }

    /**
     * A credit card can pay if is not outdated
     */
    @Override
    public boolean
	   canPay(Double amount) {
	ArgumentChecks.isTrue(amount != null && amount >= 0,
			      "CreditCard:: invalid amount");
	return !LocalDate.now()
			 .isAfter(validThru);
    }



    public LocalDate
	   getValidThru() {
	return this.validThru;
    }

}
