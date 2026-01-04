package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.exception.BusinessException;

public class DeleteLastPayroll implements Command<Integer> {

	@Override
	public Integer execute() throws BusinessException {
		LocalDate previousMonth = LocalDate.now().minusMonths(1);
		List<PayrollDto> payrolls;
		payrolls = PayrollAssembler.toDtoList(
				Factories.persistence.forPayroll().findInDates(previousMonth.getMonthValue(), previousMonth.getYear()));
		if (!payrolls.isEmpty()) {
			for (PayrollDto p : payrolls) {
				Factories.persistence.forPayroll().remove(p.id);
			}
		}
		return payrolls.size();
	}

}
