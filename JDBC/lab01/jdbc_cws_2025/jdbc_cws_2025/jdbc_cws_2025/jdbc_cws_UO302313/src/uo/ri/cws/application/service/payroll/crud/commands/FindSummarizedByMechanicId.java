package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSummarizedByMechanicId
        implements Command<List<PayrollSummaryDto>> {
    String mechanicId;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindSummarizedByMechanicId(String id) {
        checkArgumentsValidity(id);
        this.mechanicId = id;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        checkMechanicExists();
        List<PayrollRecord> pg = Factories.persistence.forPayroll()
            .findAllForMechanic(mechanicId);
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

    private void checkMechanicExists() throws BusinessException {
        BusinessChecks.exists(mg.findById(mechanicId),
            "Receiving id for a non-existing mechanic");
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
