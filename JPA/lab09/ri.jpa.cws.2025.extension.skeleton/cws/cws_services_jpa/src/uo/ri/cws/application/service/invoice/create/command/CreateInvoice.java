package uo.ri.cws.application.service.invoice.create.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoice implements Command<InvoiceDto> {
    private InvoiceRepository  invoiceRepo =
		    Factories.repository.forInvoice();
    private WorkOrderRepository workOrderRepo =
		    Factories.repository.forWorkOrder();
    private List<String> workOrderIds = new ArrayList<>();
    public CreateInvoice(List<String> workOrderIds) {
	    ArgumentChecks.isNotNull(workOrderIds,
	            "CreateInvoice:: receiving null work orders id");
	    ArgumentChecks.isTrue(!workOrderIds.isEmpty(),
	            "CreateInvoice:: work orders id list cannot be empty");

	    this.workOrderIds = new ArrayList<>(workOrderIds);
	}

    @Override
    public InvoiceDto execute() throws BusinessException {
        List<WorkOrder> workOrders = getWorkOrders();

        checkAreFinished(workOrders);
        checkNotInvoiced(workOrders);

        Long nextNumber = invoiceRepo.getNextInvoiceNumber();

        Invoice invoice = new Invoice(nextNumber, LocalDate.now(), workOrders);

        invoiceRepo.add(invoice);

        return DtoAssembler.toDto(invoice);
    }
    private List<WorkOrder> getWorkOrders() throws BusinessException {
        List<WorkOrder> res = new ArrayList<>();
        for (String id : workOrderIds) {
            ArgumentChecks.isNotNull(id,
                                     "CreateInvoice:: null id found in workorders ");
            Optional<WorkOrder> ow = workOrderRepo.findById(id);
            BusinessChecks.exists(ow,
                              "CreateInvoice:: work order not found with that id");
            res.add(ow.get());
        }
        return res;
    }

    private void checkAreFinished(List<WorkOrder> workOrders)
            throws BusinessException {
        for (WorkOrder wo : workOrders) {
            if (wo.getState() != WorkOrderState.FINISHED) {
                throw new BusinessException(
                        "Work order " + wo.getId() + " is not FINISHED");
            }
        }
    }

    private void checkNotInvoiced(List<WorkOrder> workOrders)
            throws BusinessException {
        for (WorkOrder wo : workOrders) {
            if (wo.isInvoiced()) { 
                throw new BusinessException(
                        "Work order " + wo.getId() + " is already invoiced");
            }
        }
    }

  

}
