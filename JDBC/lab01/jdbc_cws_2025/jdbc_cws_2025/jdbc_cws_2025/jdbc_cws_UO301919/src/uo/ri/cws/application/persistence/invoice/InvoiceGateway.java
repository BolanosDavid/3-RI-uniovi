package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord>{

	public class InvoiceRecord {
		public String id;
		public long version;

		public double amount;
		public double vat;
		public long number;
		public LocalDate date;
		public String state;
		
		public LocalDateTime createdat;
		public LocalDateTime updatedat;
		public String entityState;
	}

	public long findNextNumber();

}
