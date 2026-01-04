package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSummarizedByMechanicId implements Command<List<PayrollSummaryDto>> {
    private PayrollRepository repo = Factories.repository.forPayroll();
    private MechanicRepository mRepo = Factories.repository.forMechanic();
    private String mechanicId;
    public FindSummarizedByMechanicId(String mechanicId) {
	ArgumentChecks.isNotEmpty(mechanicId,"ListPayrollsOfMechanic:: reciving empty mechanic id");
	ArgumentChecks.isNotBlank(mechanicId,"ListPayrollsOfMechanic:: reciving blank mechanic id");
	this.mechanicId= mechanicId;
    }
    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	BusinessChecks.exists(mRepo.findById(mechanicId),"ListPayrollsOfMechanic:: no mechanic found with that id");
        List<Payroll> payrolls =repo.findByMechanicId(mechanicId);
        return DtoAssembler.toSummaryDtoList(payrolls);
    }
}