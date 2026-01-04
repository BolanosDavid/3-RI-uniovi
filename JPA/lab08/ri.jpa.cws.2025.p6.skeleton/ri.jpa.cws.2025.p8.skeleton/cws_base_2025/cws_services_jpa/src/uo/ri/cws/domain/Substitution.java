package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name= "TSUBSTITUTIONS" ,
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"sparepart_id","intervention_id"})
})
public class Substitution extends BaseEntity{
    // natural attributes
    private int quantity;
    @Transient private double amount;
    // accidental attributes
    @ManyToOne private SparePart sparePart;
    @ManyToOne private Intervention intervention;
    //Para JPA
    Substitution() {}
    public Substitution(SparePart sparePart, Intervention intervention,
		    int quantity) {
	ArgumentChecks.isNotNull(sparePart,"Substitution:: reciving not valid sparte part");
	ArgumentChecks.isNotNull(intervention,"Substitution:: reciving not valid intervention");
	ArgumentChecks.isTrue(quantity > 0,"Substitution:: reciving not valid quantity");
	this.sparePart = sparePart;
	this.intervention = intervention;
	this.quantity = quantity;

	Associations.Substitutes.link(sparePart, this, intervention);
	this.amount = this.quantity * this.sparePart.getPrice();
    }
    public double getAmount() {
	return amount;
    }
    void _setSparePart(SparePart sparePart) {
	this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
	this.intervention = intervention;
    }
    
    public SparePart getSparePart() {
	return this.sparePart;
    }

    public Intervention getIntervention() {
	return this.intervention;
    }

  
    public int getQuantity() {
	return quantity;
    }
    @Override
    public String toString() {
	return "Substitution [quantity=" + quantity + ", amount=" + amount
			+ "]";
    }



}
