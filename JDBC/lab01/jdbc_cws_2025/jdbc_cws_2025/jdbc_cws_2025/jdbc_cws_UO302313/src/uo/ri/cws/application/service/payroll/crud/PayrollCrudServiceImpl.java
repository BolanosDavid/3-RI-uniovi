package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastMonthPayroll;
import uo.ri.cws.application.service.payroll.crud.commands.DeleteLastMonthPayrollOfMechanic;
import uo.ri.cws.application.service.payroll.crud.commands.FindAllSummarized;
import uo.ri.cws.application.service.payroll.crud.commands.FindPayrollById;
import uo.ri.cws.application.service.payroll.crud.commands.FindSummarizedByMechanicId;
import uo.ri.cws.application.service.payroll.crud.commands.FindSummarizedByProfGroupName;
import uo.ri.cws.application.service.payroll.crud.commands.GenerateLastMonthPayroll;
import uo.ri.cws.application.service.payroll.crud.commands.GeneratePayrollsAtDate;
import uo.ri.util.exception.BusinessException;

public class PayrollCrudServiceImpl implements PayrollService {
    CommandExecutor executor = new CommandExecutor();

    @Override
    public List<PayrollDto> generateForPreviousMonth()
        throws BusinessException {
        return executor.execute(new GenerateLastMonthPayroll());
    }

    @Override
    public List<PayrollDto> generateForPreviousMonthOf(LocalDate present)
        throws BusinessException {
        return executor.execute(new GeneratePayrollsAtDate(present));
    }

    @Override
    public void deleteLastGeneratedOfMechanicId(String mechanicId)
        throws BusinessException {
        executor.execute(new DeleteLastMonthPayrollOfMechanic(mechanicId));

    }

    @Override
    public int deleteLastGenerated() throws BusinessException {
        return executor.execute(new DeleteLastMonthPayroll());
    }

    @Override
    public Optional<PayrollDto> findById(String id) throws BusinessException {
        return executor.execute(new FindPayrollById(id));
    }

    @Override
    public List<PayrollSummaryDto> findAllSummarized()
        throws BusinessException {
        return executor.execute(new FindAllSummarized());
    }

    @Override
    public List<PayrollSummaryDto> findSummarizedByMechanicId(String id)
        throws BusinessException {
        return executor.execute(new FindSummarizedByMechanicId(id));
    }

    @Override
    public List<PayrollSummaryDto>
            findSummarizedByProfessionalGroupName(String name)
                throws BusinessException {
        return executor.execute(new FindSummarizedByProfGroupName(name));
    }

}
