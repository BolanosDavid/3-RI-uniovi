package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class CreateInvoice implements Command<InvoiceDto> {
	private List<String> workOrderIds;
	private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	private InvoiceGateway ig = Factories.persistence.forInvoice();

	public CreateInvoice(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds, "WorkOrderIds cannot be null");
		ArgumentChecks.isTrue(!workOrderIds.isEmpty(), "WorkOrderIds cannot be empty");
		for (String id : workOrderIds) {
			ArgumentChecks.isNotNull(id, "WorkOrderId cannor be null");
			ArgumentChecks.isNotBlank(id, "WorkOrderId cannot be blank");
		}
		this.workOrderIds = workOrderIds;
	}

	public InvoiceDto execute() throws BusinessException {
		checkValues();

		// Inicializate variables
		String idInvoice = UUID.randomUUID().toString();
		long numberInvoice = ig.findNextNumber();
		LocalDate dateInvoice = LocalDate.now();
		double amount = 0.0;
		for (String workOrderId : workOrderIds) {
			Optional<WorkOrderRecord> optWor = wog.findById(workOrderId);
			if (optWor.isPresent()) {
				amount += optWor.get().amount;
			}
		}
		double vat = vatPercentage(dateInvoice);
		double vatAmount = amount * (vat / 100.0);
		double total = Rounds.toCents(amount + vatAmount);

		InvoiceRecord ir = createInvoiceRecord(idInvoice, numberInvoice, dateInvoice, vatAmount, total);
		createWorkOrderRecord(idInvoice);
		return InvoiceAssembler.toDto(ir);
	}

	private void checkValues() throws BusinessException {
		for (String id : workOrderIds) {
			Optional<WorkOrderRecord> wor = wog.findById(id);
			BusinessChecks.exists(wor, "WorkOrder doesn´t exists");
			BusinessChecks.isTrue("FINISHED".equalsIgnoreCase(wor.get().state), "Not all work orders are finished");
		}
	}

	private void createWorkOrderRecord(String idInvoice) {
		for (String workOrderId : workOrderIds) {
			Optional<WorkOrderRecord> optWor = wog.findById(workOrderId);
			if (optWor.isPresent()) {
				WorkOrderRecord wor = optWor.get();
				wor.invoiceId = idInvoice;
				wor.state = "INVOICED";
				wor.version += 1;
				wor.updatedAt = LocalDateTime.now();
				wog.update(wor);
			}
		}
	}

	private InvoiceRecord createInvoiceRecord(String idInvoice, long numberInvoice, LocalDate dateInvoice,
			double vatAmount, double total) {
		InvoiceRecord ir = new InvoiceRecord();
		ir.id = idInvoice;
		ir.number = numberInvoice;
		ir.date = dateInvoice;
		ir.vat = vatAmount;
		ir.amount = total;
		ir.state = "NOT_YET_PAID";
		ir.version = 1L;
		ir.createdat = LocalDateTime.now();
		ir.updatedat = LocalDateTime.now();
		ir.entityState = "ACTIVE";
		ig.add(ir);
		return ir;
	}

	private double vatPercentage(LocalDate d) {
		return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;
	}
}