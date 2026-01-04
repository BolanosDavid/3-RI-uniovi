package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindWorkOrdersByClientNif 
			     implements Command<List<InvoicingWorkOrderDto>> {
    private String nif;
    private WorkOrderRepository repo = Factories.repository.forWorkOrder();
    public FindWorkOrdersByClientNif(String nif) {
	ArgumentChecks.isNotEmpty(nif,
	                          "FindWorkOrdersByClientNif:: receiving empty nif");
	ArgumentChecks.isNotBlank(nif,
	                          "FindWorkOrdersByClientNif:: receiving blank nif");
	this.nif = nif;
    }
    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	return DtoAssembler
			.toInvoicingWorkOrderDtoList(repo.findByClientNif(nif));
    }

}
