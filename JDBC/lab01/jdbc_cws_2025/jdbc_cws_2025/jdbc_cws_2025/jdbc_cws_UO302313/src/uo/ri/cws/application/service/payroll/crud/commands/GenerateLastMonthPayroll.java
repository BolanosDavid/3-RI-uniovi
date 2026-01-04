package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.util.exception.BusinessException;

public class GenerateLastMonthPayroll implements Command<List<PayrollDto>> {

    @Override
    public List<PayrollDto> execute() throws BusinessException {
        return Factories.service.forPayrollService()
            .generateForPreviousMonthOf(LocalDate.now());
    }

}
