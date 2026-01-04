package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class InvoiceAssembler {

    public static InvoiceDto toDto(InvoiceRecord ir) {
        InvoiceDto dto = new InvoiceDto();
        dto.id = ir.id;
        dto.number = ir.number;
        dto.date = ir.date;
        dto.vat = ir.vat;
        dto.amount = ir.amount;
        dto.state = ir.state;
        dto.version = ir.version;
        return dto;
    }

}
