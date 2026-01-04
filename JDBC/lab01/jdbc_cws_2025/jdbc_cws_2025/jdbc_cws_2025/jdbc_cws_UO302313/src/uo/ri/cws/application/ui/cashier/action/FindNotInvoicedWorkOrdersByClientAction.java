package uo.ri.cws.application.ui.cashier.action;

import java.util.List;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {

    /**
     * Process: - Ask customer nif - Display all uncharged workorder (status <>
     * 'INVOICED'). For each workorder, display id, vehicle id, date, status,
     * amount and description
     */

    @Override
    public void execute() throws BusinessException {
        String nif = Console.readString("Client nif ");

        Console.println("\nClient's not invoiced work orders\n");
        List<InvoicingWorkOrderDto> l =
            Factories.service.forCreateInvoiceService()
                .findNotInvoicedWorkOrdersByClientNif(nif);
        l.stream()
            .forEachOrdered((InvoicingWorkOrderDto i) -> {
                Console
                    .printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f%n", i.id, i.description, i.date, i.state, i.amount);
            });

    }

}