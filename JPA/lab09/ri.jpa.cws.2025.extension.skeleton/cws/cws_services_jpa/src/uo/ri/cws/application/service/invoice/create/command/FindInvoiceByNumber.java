package uo.ri.cws.application.service.invoice.create.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>>{
    private Long number;
    public FindInvoiceByNumber(Long number) {
	ArgumentChecks.isTrue(number >= 0,
			"FindInvoiceByNumber:: receiving not valid number");
	this.number = number;
		
    }
    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {
	Optional<Invoice> invoice = Factories.repository.forInvoice()
			.findByNumber(number);
	if(invoice.isPresent()) {
	    return Optional.of(DtoAssembler.toDto(invoice.get()));
	}else {
	    return Optional.empty();
	}
    }

}
