package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSummarizedByProfGroupName
        implements Command<List<PayrollSummaryDto>> {
    private String name;
    private ProfessionalGroupGateway pgg =
        Factories.persistence.forProfessionalGroup();

    public FindSummarizedByProfGroupName(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new IllegalArgumentException("Receving invalid name");
        this.name = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        checkExists();
        List<PayrollRecord> pg = Factories.persistence.forPayroll()
            .findAllByProfGroupName(name);
        if (pg.isEmpty())
            return List.of();
        return pg.stream()
            .map(r -> {
                PayrollSummaryDto dto = PayrollAssembler.toPayrollSummaryDto(r);
                Calculated calculos = PayrollCalculator.fromRecord(r);
                dto.netSalary = calculos.netSalary;
                return dto;
            })
            .toList();
    }

    private void checkExists() throws BusinessException {
        BusinessChecks.exists(pgg.findByName(name),
                "receving a non-existing name");
    }
}
