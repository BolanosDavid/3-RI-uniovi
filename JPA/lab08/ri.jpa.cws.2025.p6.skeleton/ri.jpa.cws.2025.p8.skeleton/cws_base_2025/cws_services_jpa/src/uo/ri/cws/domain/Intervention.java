package uo.ri.cws.domain;

import java.time.LocalDateTime; 
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
@Entity
@Table(name= "TINTERVENTIONS" ,
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"date","workorder_id","mechanic_id"})
})
public class Intervention extends BaseEntity {
    // natural attributes
    @Column(unique=true)private LocalDateTime date;
    private int minutes;
    @Transient private double amount;

    // accidental attributes
    @ManyToOne private WorkOrder workOrder;
    @ManyToOne private Mechanic mechanic;
    @OneToMany(mappedBy="intervention") private Set<Substitution> substitutions = new HashSet<>();

    //Para JPA
    Intervention() {}
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


    Mechanic _getMechanic() {
	return this.mechanic;
    }

    WorkOrder _getWorkOrder() {
	return this.workOrder;
    }
    @Override
    public String toString() {
        return "Intervention [id=" + getId() + ", date=" + date
               + ", minutes=" + minutes + "]";
    }

}
