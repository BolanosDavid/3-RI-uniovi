package uo.ri.cws.domain;

import java.time.LocalDate; 
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Invoice {

    public enum InvoiceState {
	NOT_YET_PAID, PAID
    }

    // natural attributes
    private Long number;
    private LocalDate date;

    private double amount;
    private double vat;
    private InvoiceState state = InvoiceState.NOT_YET_PAID;

    // accidental attributes
    private Set<WorkOrder> workOrders = new HashSet<>();
    private Set<Charge> charges = new HashSet<>();

    public Invoice(Long number) {
	this(number, LocalDate.now(), List.of(), List.of());
    }

    public Invoice(Long number, LocalDate date) {
	this(number, date, List.of(), List.of());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
	this(number, LocalDate.now(), workOrders, List.of());
    }
    public Invoice(Long number, LocalDate now, List<WorkOrder> wos) {
   	this(number,now,wos,List.of());
       }
    // full constructor
    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders,
		    List<Charge> chages) {
	ArgumentChecks.isTrue(number >= 0, "Invoice:: not valid number");
	ArgumentChecks.isNotNull(date, "Invoice:: not valid date");
	ArgumentChecks.isNotNull(workOrders,"Invoice:: Reciving null work orders ");
	ArgumentChecks.isNotNull(chages,"Invoice:: reciving null charges");
	this.number = number;
	this.date = date;
	this.vat = vatPercentage(date);
	workOrders.forEach(this::addWorkOrder);

    }

   

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
	this.amount = workOrders.stream().mapToDouble(WorkOrder::getAmount)
			.sum() * (1 + getVat() / 100);
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the amount
     * and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     * @throws IllegalStateException if the workorder status is not FINISHED
     */
    public void addWorkOrder(WorkOrder workOrder) {
	ArgumentChecks.isNotNull(workOrder,"Invoice: reciving null work order to add");
	StateChecks.isTrue(workOrder.isFinished(),
			"Invoice:: work order must be FINISHED before invoicing");
	Associations.Bills.link(this, workOrder);
	workOrder.markAsInvoiced();
	computeAmount();
    }

    /**
     * Removes a work order from the invoice, updates the workorder state and
     * recomputes amount and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException    if the invoice status is not
     *                                  NOT_YET_PAID
     * @throws IllegalArgumentException if the invoice does not contain the
     *                                  workorder
     */
    public void removeWorkOrder(WorkOrder workOrder) {
	StateChecks.isTrue(isNotSettled(),
			"Invoice:: invoice status must be NOT_YET_PAID");
	ArgumentChecks.isTrue(workOrders.contains(workOrder),
			"Invoice:: can't find the workorder");
	workOrder.markBackToFinished();
	Associations.Bills.unlink(this, workOrder);
	computeAmount();
    }

    /**
     * Marks the invoice as PAID, but
     * 
     * @throws IllegalStateException if - Is already settled - Or the amounts
     *                               paid with charges to payment means do not
     *                               cover the total of the invoice
     */
    public void settle() {
	if (state == InvoiceState.PAID)
	    throw new IllegalArgumentException(
			    "Invoice:: trying to settle an already settled invoice");
	if (getCharges().stream().mapToDouble(Charge::getAmount)
			.sum() < getAmount())
	    throw new IllegalArgumentException(
			    "Invoice:: charges does not cover the total of the invoice");
	this.state = InvoiceState.PAID;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    public Set<Charge> getCharges() {
	return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
	return charges;
    }

    public Long getNumber() {
	return number;
    }

    public LocalDate getDate() {
	return date;
    }

    public double getAmount() {
	return amount;
    }

    public double getVat() {
	return vat;
    }

    public boolean isNotSettled() {
	return state.equals(Invoice.InvoiceState.NOT_YET_PAID);
    }

    @Override
    public String toString() {
	return "Invoice [number=" + number + ", date=" + date + ", amount="
			+ amount + ", vat=" + vat + ", state=" + state
			+ ", workOrders=" + workOrders + "]";
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
	Invoice other = (Invoice) obj;
	return Objects.equals(number, other.number);
    }

    private double vatPercentage(LocalDate d) {
	return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;
    }

}
