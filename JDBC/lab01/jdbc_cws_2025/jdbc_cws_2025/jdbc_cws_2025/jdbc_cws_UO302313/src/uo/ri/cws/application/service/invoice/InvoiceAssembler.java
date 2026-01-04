package uo.ri.cws.application.service.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

public class InvoiceAssembler {

    public static InvoiceRecord toRecord(InvoiceDto dto) {
        InvoiceRecord r = new InvoiceRecord();
        r.id = dto.id;
        r.number = dto.number;
        r.date = dto.date;
        r.vat = dto.vat;
        r.amount = dto.amount;
        r.state = dto.state;
        r.version = dto.version;

        return r;
    }

    public static InvoiceDto toDto(InvoiceRecord r) {
        InvoiceDto dto = new InvoiceDto();
        dto.id = r.id;
        dto.number = r.number;
        dto.date = r.date;
        dto.vat = r.vat;
        dto.amount = r.amount;
        dto.state = r.state;
        dto.version = r.version;

        return dto;
    }

    public static InvoiceRecord createRecord(String idInvoice,
                                             long numberInvoice,
                                             LocalDate dateInvoice,
                                             double vatAmount,
                                             double total,
                                             String status,
                                             long l,
                                             String entityState) {
        InvoiceRecord ir = new InvoiceRecord();
        ir.id = idInvoice;
        ir.number = numberInvoice;
        ir.date = dateInvoice;
        ir.vat = vatAmount;
        ir.amount = total;
        ir.state = status;
        ir.version = 1L;
        ir.createdAt = LocalDateTime.now();
        ir.updatedAt = LocalDateTime.now();
        ir.entityState = entityState;
        return ir;
    }
}
