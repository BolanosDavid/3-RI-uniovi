package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.exception.BusinessException;

public class LisFindAllSummarized implements Command<List<PayrollSummaryDto>> {

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		List<PayrollSummaryDto> payrollsSummary = new ArrayList<>();
		List<PayrollDto> payrolls;
		payrolls = PayrollAssembler.toDtoList(Factories.persistence.forPayroll().findAll());
		for(PayrollDto p: payrolls) {
			payrollsSummary.add(PayrollAssembler.toSummaryDto(p));
		}
		return payrollsSummary;
	}

}
