package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Substitution {
    // natural attributes
    private int quantity;
    private double amount;
    // accidental attributes
    private SparePart sparePart;
    private Intervention intervention;

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

    @Override
    public String toString() {
	return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
			+ ", intervention=" + intervention + "]";
    }



}
