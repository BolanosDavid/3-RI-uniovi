package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class WorkOrder {
    public enum WorkOrderState {
	OPEN, ASSIGNED, FINISHED, INVOICED
    }

    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;
    private WorkOrderState state = WorkOrderState.OPEN;

    // accidental attributes
    private Vehicle vehicle;
    private Mechanic mechanic;
    private Invoice invoice;

    public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
	ArgumentChecks.isNotNull(vehicle, "WorkOrder:: not acceptable vehicle");
	ArgumentChecks.isNotEmpty(description,
			"WorkOrder:: not acceptable description");
	ArgumentChecks.isNotNull(date, "WorkOrder:: not acceptable date");
	this.date = date.truncatedTo(ChronoUnit.MILLIS);
	this.description = description;
	Associations.Fixes.link(vehicle, this);
    }

    public WorkOrder(Vehicle vehicle, String description) {
	this(vehicle, LocalDateTime.now(),description );
    }

    public WorkOrder(Vehicle vehicle) {
	this(vehicle,LocalDateTime.now(),"nodescription");
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime now) {
	this(vehicle,now,"nodescription");
    }

    public LocalDateTime getDate() {
	return date;
    }

    public String getDescription() {
	return description;
    }

    public double getAmount() {
	return amount;
    }

    public Vehicle getVehicle() {
	return vehicle;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, vehicle);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	WorkOrder other = (WorkOrder) obj;
	return Objects.equals(date, other.date)
			&& Objects.equals(vehicle, other.vehicle);
    }

    private Set<Intervention> interventions = new HashSet<>();

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
	StateChecks.isTrue(isFinished(),"WorkOrder:: work order must be finished before invoiced");
	StateChecks.isTrue(getInvoice() != null,"WorkOrder:: work order not linked with the invoice");
	this.state = WorkOrderState.INVOICED;
    }

    /**
     * Given the right conditions unlinks the workorder and the mechanic,
     * changes the state to FINISHED and computes the amount
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or
     */
    public void markAsFinished() {
	StateChecks.isTrue(isAssigned(),"WorkOrder:: work order must be assigned before marked as finished");
	calculateAmount();
	
	Associations.Assigns.unlink(mechanic,this);
	this.state =WorkOrderState.FINISHED;
    }

    private void calculateAmount() {
	this.amount = getInterventions().stream().mapToDouble(Intervention::getAmount).sum();
	
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or
     */
    public void markBackToFinished() {
	StateChecks.isTrue(isInvoiced(),"WorkOrder:: work order must be invoiced before changing it back to finished");
	this.state = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its state
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN state,
     *                               or
     */
    public void assignTo(Mechanic mechanic) {
	ArgumentChecks.isNotNull(mechanic,"WorkOrder:: reciving null mechanic to assign work order");
	StateChecks.isTrue(isOpen(),"WorkOrder:: work order must be opened before assigned");
	Associations.Assigns.link(mechanic, this);
	this.state = WorkOrderState.ASSIGNED;
    }

   

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * state back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state
     */
    public void unassign() {
	StateChecks.isTrue(isAssigned(),"WorkOrder:: work order must be assigned first");
	Associations.Assigns.unlink(mechanic,this);
	this.state = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic it first have to be
     * moved back to OPEN state.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               state
     */
    public void reopen() {
	StateChecks.isTrue(isFinished(),"WorkOrder:: Work order must be finished first before moving it back to OPEN state");
	this.state=WorkOrderState.OPEN;
    }

    public boolean isAssigned() {
	return this.state == WorkOrderState.ASSIGNED;
    }
    public boolean isOpen() {
   	return this.state == WorkOrderState.OPEN;
       }
    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    public boolean isFinished() {
	return this.state == WorkOrderState.FINISHED;
    }

    public boolean isInvoiced() {
	return this.state == WorkOrderState.INVOICED;
    }

}
