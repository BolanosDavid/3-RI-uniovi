package uo.ri.cws.application.service.invoice;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoicingWorkOrderAssembler {

    public static InvoicingWorkOrderDto toDto(InvoicingWorkOrderRecord r) {
        InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
        dto.id = r.id;
        dto.description = r.description;
        dto.date = r.date;
        dto.state = r.state;
        dto.amount = r.amount;
        return dto;
    }
}
