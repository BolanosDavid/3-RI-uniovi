package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoiceAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class CreateInvoice implements Command<InvoiceDto> {

    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private InvoiceGateway ig = Factories.persistence.forInvoice();
    private List<String> workOrderIds;

    public CreateInvoice(List<String> workOrderIds) {
        checkWorkOrderValidity(workOrderIds);
        this.workOrderIds = workOrderIds;
    }

    public InvoiceDto execute() throws BusinessException {
        ArrayList<Optional<WorkOrderRecord>> lista = checkWorkOrderStatus();
        InvoiceRecord ir = addInvoice(lista);
        return InvoiceAssembler.toDto(ir);
    }

    private InvoiceRecord
            addInvoice(ArrayList<Optional<WorkOrderRecord>> lista) {
        String idInvoice = UUID.randomUUID()
            .toString();
        long numberInvoice = ig.findNextNumber();
        LocalDate dateInvoice = LocalDate.now();
        double amount = 0.0;
        amount = calculateAmount(amount,
            lista);
        double vat = vatPercentage(dateInvoice);
        double vatAmount = amount * ( vat / 100.0 );
        double total = Rounds.toCents(amount * vatAmount);
        InvoiceRecord ir = InvoiceAssembler.createRecord(idInvoice,
            numberInvoice,
            dateInvoice,
            vatAmount,
            total,
            "NOT_YET_PAID",
            1L,
            "ACTIVE");
        ig.add(ir);
        updateWorkOrders(idInvoice);
        return ir;
    }

    private void updateWorkOrders(String idInvoice) {
        for (String workOrderId : workOrderIds) {
            Optional<WorkOrderRecord> optWor = wog.findById(workOrderId);
            if (optWor.isPresent()) {
                WorkOrderRecord wor = optWor.get();
                wor.invoiceId = idInvoice;
                wor.state = "INVOICED";
                wor.version += 1;
                wog.update(wor);
            }
        }
    }

    private ArrayList<Optional<WorkOrderRecord>> checkWorkOrderStatus()
        throws BusinessException {
        ArrayList<Optional<WorkOrderRecord>> lista =
            new ArrayList<Optional<WorkOrderRecord>>();
        for (String id : workOrderIds) {
            Optional<WorkOrderRecord> wor = wog.findById(id);
            BusinessChecks.exists(wor,
                "Receiving not existing work orders");
            BusinessChecks.isTrue("FINISHED".equalsIgnoreCase(wor.get().state),
                "Not all work orders are finished");
            lista.add(wor);
        }
        return lista;
    }

    private double calculateAmount(double amount,
                                   List<Optional<WorkOrderRecord>> list) {
        for (Optional<WorkOrderRecord> i : list) {
            amount += i.get().amount;
        }
        return amount;
    }

    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01")
            .isBefore(d) ? 21.0 : 18.0;
    }

    private void checkWorkOrderValidity(List<String> workOrderIds) {
        if (workOrderIds == null)
            throw new IllegalArgumentException("WorkOrderIds cannot be null");
        if (workOrderIds.isEmpty())
            throw new IllegalArgumentException("WorkOrderIds cannot be empty");
        workOrderIds.forEach(i -> {
            if (i == null)
                throw new IllegalArgumentException(
                        "Receiving null Work Order Id");
        });
    }
}
