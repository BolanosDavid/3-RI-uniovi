package uo.ri.cws.application.service.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.workOrder.WorkOrderAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClientNif implements Command<List<InvoicingWorkOrderDto>> {

	private String nif;
	WorkOrderGateway wog = Factories.persistence.forWorkOrder();

	public FindNotInvoicedWorkOrdersByClientNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}

	public List<InvoicingWorkOrderDto> execute() {
		List<WorkOrderRecord> records = wog.findNotInvoicedByClientNif(nif);
		List<InvoicingWorkOrderDto> res = new ArrayList<>();
		for (WorkOrderRecord r : records) {
			res.add(WorkOrderAssembler.toDto(r));
		}
		return res;
	}
}
