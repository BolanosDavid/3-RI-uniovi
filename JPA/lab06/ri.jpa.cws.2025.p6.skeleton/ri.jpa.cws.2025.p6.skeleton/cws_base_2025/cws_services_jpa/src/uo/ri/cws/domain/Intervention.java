package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Intervention {
    // natural attributes
    private LocalDateTime date;
    private int minutes;
    private double amount;

    // accidental attributes
    private WorkOrder workOrder;
    private Mechanic mechanic;
    private Set<Substitution> substitutions = new HashSet<>();

    public Intervention(Mechanic m, WorkOrder w, int min) {
	this(m, w, LocalDateTime.now(), min);
    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder,
		    LocalDateTime date, int minutes) {
	ArgumentChecks.isNotNull(mechanic, "Intervention:: not valid mechanic");
	ArgumentChecks.isNotNull(workOrder,"Intervention:: not valid mechanic");
	ArgumentChecks.isNotNull(date, "Intervention:: not valid date");
	ArgumentChecks.isTrue(minutes >= 0, "Intervention:: not valid minutes");

	this.date = date.truncatedTo(ChronoUnit.MILLIS);
	this.minutes = minutes;
	Associations.Intervenes.link(workOrder, this, mechanic);
	substitutions.forEach(substitution -> {
	    Associations.Substitutes.link(substitution.getSparePart(),
			    substitution, this);
	});
	
    }

    public double getAmount() {
	double pricePerHour = workOrder.getVehicle().getVehicleType().getPricePerHour();
	double substitutionsPrice = substitutions.stream().mapToDouble(Substitution::getAmount).sum();
	this.amount =  (pricePerHour * minutes) / 60 + substitutionsPrice;
	return amount;
    }
    public LocalDateTime getDate() {
	return date;
    }

    public int getMinutes() {
	return minutes;
    }

    public WorkOrder getWorkOrder() {
	return workOrder;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    void _setWorkOrder(WorkOrder workOrder) {
	this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<Substitution>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return substitutions;
    }

    @Override
    public String toString() {
	return "Intervention [date=" + date + ", minutes=" + minutes
			+ ", workOrder=" + workOrder + ", mechanic=" + mechanic
			+ "]";
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Intervention other = (Intervention) obj;
	return Objects.equals(date, other.date)
			&& Objects.equals(mechanic, other.mechanic)
			&& Objects.equals(workOrder, other.workOrder);
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, mechanic, workOrder);
    }

    Mechanic _getMechanic() {
	return this.mechanic;
    }

    WorkOrder _getWorkOrder() {
	return this.workOrder;
    }


}
