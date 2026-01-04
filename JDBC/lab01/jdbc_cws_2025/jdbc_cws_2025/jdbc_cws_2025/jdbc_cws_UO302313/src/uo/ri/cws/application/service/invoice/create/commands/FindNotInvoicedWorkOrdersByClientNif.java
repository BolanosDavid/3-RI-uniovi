package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;
import java.util.ArrayList;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.InvoicingWorkOrderAssembler;

public class FindNotInvoicedWorkOrdersByClientNif
        implements Command<List<InvoicingWorkOrderDto>> {
    private String nif;

    public FindNotInvoicedWorkOrdersByClientNif(String nif) {
        if (nif == null)
            throw new IllegalArgumentException("Nif cannot be null");
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {
        List<InvoicingWorkOrderRecord> ir = Factories.persistence.forInvoice()
            .findNotInvoicedWorkOrdersByClientNif(nif);
        List<InvoicingWorkOrderDto> listaFinal =
            new ArrayList<InvoicingWorkOrderDto>();
        ir.stream()
            .map(InvoicingWorkOrderAssembler::toDto)
            .forEach(listaFinal::add);
        return listaFinal;
    }
}
