package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;
import uo.ri.util.exception.BusinessException;

public class FindPayrollById implements Command<Optional<PayrollDto>> {
    String id;

    public FindPayrollById(String id) {
        checkArgumentsValidity(id);
        this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
        return getPayrollDto();
    }

    private Optional<PayrollDto> getPayrollDto() throws BusinessException {

        Optional<PayrollRecord> p = Factories.persistence.forPayroll()
            .findById(id);
        if (p.isEmpty())
            return Optional.empty();
        Calculated c = PayrollCalculator.fromRecord(p.get());
        PayrollDto dto = PayrollAssembler.toDto(p)
            .get();
        dto.totalDeductions = c.totalDeductions;
        dto.grossSalary = c.grossSalary;
        dto.netSalary = c.netSalary;
        return Optional.of(dto);
    }

    private void checkArgumentsValidity(String id) {
        if (id == null)
            throw new IllegalArgumentException("Receving null mechanic id");
        if (id.isEmpty())
            throw new IllegalArgumentException("Receving empty mechanic id");
        if (id.isBlank())
            throw new IllegalArgumentException("Receving blank mechanic id");
    }

}
