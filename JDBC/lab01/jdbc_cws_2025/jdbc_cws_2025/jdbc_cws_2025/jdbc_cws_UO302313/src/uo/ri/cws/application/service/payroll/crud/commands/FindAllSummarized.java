package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;
import uo.ri.util.exception.BusinessException;

public class FindAllSummarized implements Command<List<PayrollSummaryDto>> {

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        List<PayrollRecord> pg = Factories.persistence.forPayroll()
            .findAll();
        return pg.stream()
            .map(r -> {
                PayrollSummaryDto dto = PayrollAssembler.toPayrollSummaryDto(r);
                Calculated calculos = PayrollCalculator.fromRecord(r);
                dto.netSalary = calculos.netSalary;
                return dto;
            })
            .toList();
    }

}
