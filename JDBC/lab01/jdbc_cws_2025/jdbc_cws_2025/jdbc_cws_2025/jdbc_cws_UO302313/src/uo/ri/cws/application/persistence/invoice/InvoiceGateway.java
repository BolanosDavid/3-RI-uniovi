package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {
    public List<InvoicingWorkOrderRecord>
            findNotInvoicedWorkOrdersByClientNif(String nif);

    public long findNextNumber();

    public class InvoiceRecord extends BasicRecord {
        public Long number;
        public LocalDate date;
        public double vat;
        public double amount;
        public String state;
    }

    public class InvoicingWorkOrderRecord {
        public String id;
        public LocalDateTime createdAt;
        public String description;
        public LocalDateTime date;
        public String state;
        public double amount;
    }

}
