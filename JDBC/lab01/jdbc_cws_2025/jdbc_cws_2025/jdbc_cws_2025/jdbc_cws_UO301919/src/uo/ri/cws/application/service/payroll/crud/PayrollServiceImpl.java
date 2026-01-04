package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastPayroll;
import uo.ri.cws.application.service.payroll.crud.commands.DeletePayrollForMechanic;
import uo.ri.cws.application.service.payroll.crud.commands.GeneratePayrolls;
import uo.ri.cws.application.service.payroll.crud.commands.LisFindAllSummarized;
import uo.ri.cws.application.service.payroll.crud.commands.ListFindById;
import uo.ri.cws.application.service.payroll.crud.commands.ListFindSummarizedByMechanic;
import uo.ri.cws.application.service.payroll.crud.commands.ListFindSummarizedByProfessionalName;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService{
	private CommandExecutor exec = new CommandExecutor();

	@Override
	public List<PayrollDto> generateForPreviousMonth() throws BusinessException {
		return generateForPreviousMonthOf(LocalDate.now());
	}

	@Override
	public List<PayrollDto> generateForPreviousMonthOf(LocalDate present) throws BusinessException {
		return exec.execute(new GeneratePayrolls(present));
	}

	@Override
	public void deleteLastGeneratedOfMechanicId(String mechanicId) throws BusinessException {
		exec.execute(new DeletePayrollForMechanic(mechanicId));
	}

	@Override
	public int deleteLastGenerated() throws BusinessException {
		return exec.execute(new DeleteLastPayroll());
	}

	@Override
	public Optional<PayrollDto> findById(String id) throws BusinessException {
		return exec.execute(new ListFindById(id));
	}

	@Override
	public List<PayrollSummaryDto> findAllSummarized() throws BusinessException {
		return exec.execute(new LisFindAllSummarized());
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByMechanicId(String id) throws BusinessException {
		return exec.execute(new ListFindSummarizedByMechanic(id));
	}

	@Override
	public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(String name) throws BusinessException {
		return exec.execute(new ListFindSummarizedByProfessionalName(name));
	}

}
