package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name="TCREDITCARDS")
public class CreditCard extends PaymentMean {
    @Column(unique=true)private String number;
    private String type;
    private LocalDate validThru;

    CreditCard() {}
    public CreditCard(String number, String type, LocalDate validThru) {
	ArgumentChecks.isNotEmpty(number, "CreditCard:: reciving invalid name");
	ArgumentChecks.isNotEmpty(type, "CreditCard:: reciving invalid type");
	ArgumentChecks.isNotNull(validThru,"CreditCard:: reciving invalid valid date");
	
	
	this.number = number;
	this.type = type;
	this.validThru = validThru;
    }

    public String getNumber() {
	return number;
    }

    public String getType() {
	return type;
    }

    /**
     * A credit card can pay if is not outdated
     */
    @Override
    public boolean canPay(Double amount) {
	ArgumentChecks.isTrue(amount != null && amount >= 0, "CreditCard:: invalid amount");
	return !LocalDate.now().isAfter(validThru);
    }

    @Override
    public String toString() {
	return "CreditCard [number=" + number + ", type=" + type
			+ ", validThru=" + validThru + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(number);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CreditCard other = (CreditCard) obj;
	return Objects.equals(number, other.number);
    }
    public LocalDate getValidThru() {
	return this.validThru;
    }

}
