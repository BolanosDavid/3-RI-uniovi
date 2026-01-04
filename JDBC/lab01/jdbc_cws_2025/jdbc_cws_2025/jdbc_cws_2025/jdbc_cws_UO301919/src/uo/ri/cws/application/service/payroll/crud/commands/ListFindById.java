package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListFindById implements Command<Optional<PayrollDto>> {

	private String id;
	
	public ListFindById(String id) {
		ArgumentChecks.isNotBlank(id, "The payroll id cannot be blank");
		this.id = id;
	}



	@Override
	public Optional<PayrollDto> execute() throws BusinessException {
		return PayrollAssembler.toDto(Factories.persistence.forPayroll().findById(id));
	}

}
