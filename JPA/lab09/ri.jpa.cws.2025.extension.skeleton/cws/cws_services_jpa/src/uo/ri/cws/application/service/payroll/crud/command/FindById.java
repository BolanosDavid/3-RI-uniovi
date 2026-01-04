package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindById implements Command<Optional<PayrollDto>> {
    private PayrollRepository repo = Factories.repository.forPayroll();
    private String payrollId;
    public FindById(String payrollId) {
	ArgumentChecks.isNotEmpty(payrollId,"FindPayrollById:: Receiving empty payroll id");
	ArgumentChecks.isNotBlank(payrollId,"FindPayrollById:: Receiving blank payroll id");
	this.payrollId = payrollId;
    }
    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
        return repo.findById(payrollId)
                   .map(DtoAssembler::toDto);
    }
}