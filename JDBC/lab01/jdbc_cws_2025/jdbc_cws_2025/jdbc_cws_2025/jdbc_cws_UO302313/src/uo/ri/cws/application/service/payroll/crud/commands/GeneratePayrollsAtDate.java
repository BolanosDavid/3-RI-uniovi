package uo.ri.cws.application.service.payroll.crud.commands;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator;
import uo.ri.cws.application.service.payroll.crud.util.PayrollCalculator.Calculated;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrollsAtDate implements Command<List<PayrollDto>> {

    private final ContractGateway cg = Factories.persistence.forContract();
    private final PayrollGateway pg = Factories.persistence.forPayroll();
    private final ProfessionalGroupGateway pgg =
        Factories.persistence.forProfessionalGroup();
    private final WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private final LocalDate present;

    public GeneratePayrollsAtDate(LocalDate present) {
        if (present == null)
            throw new IllegalArgumentException("present is null");
        this.present = present;
    }

    @Override
    public List<PayrollDto> execute() throws BusinessException {
        YearMonth target = YearMonth.from(present)
            .minusMonths(1);
        LocalDate start = target.atDay(1);
        LocalDate end = target.atEndOfMonth();
        LocalDate next = target.plusMonths(1)
            .atDay(1);

        List<PayrollDto> out = new ArrayList<>();
        List<ContractRecord> contracts = getContracts(start,
            end);

        for (ContractRecord c : contracts) {
            if (alreadyHasPayroll(c,
                start,
                next)) {
                continue;
            }
            out.add(createPayrollFor(c,
                start,
                end));
        }
        return out;
    }

    private List<ContractRecord> getContracts(LocalDate start,
                                              LocalDate end) {
        List<ContractRecord> active = cg.findActiveAt(Date.valueOf(end));
        List<ContractRecord> terminated =
            cg.findTerminatedBetween(Date.valueOf(start),
                Date.valueOf(end));
        List<ContractRecord> contracts = Stream.concat(active.stream(),
            terminated.stream())
            .distinct()
            .toList();
        return contracts;
    }

    private boolean alreadyHasPayroll(ContractRecord c,
                                      LocalDate start,
                                      LocalDate next) {
        return pg.findContractBetween(c.id,
            Date.valueOf(start),
            Date.valueOf(next))
            .isPresent();
    }

    private PayrollDto createPayrollFor(ContractRecord c,
                                        LocalDate start,
                                        LocalDate end)
        throws BusinessException {
        ProfessionalGroupRecord group = groupOf(c);
        String payrollId = UUID.randomUUID()
            .toString();
        Calculated calculated = PayrollCalculator.fromContract(c,
            group,
            start,
            end,
            wog.findByInterventionMechanicId(c.mechanicId));
        PayrollRecord r = PayrollAssembler.toRecord(payrollId,
            calculated,
            c.id,
            end);
        pg.add(r);
        PayrollDto d = PayrollAssembler.toDto(payrollId,
            calculated,
            c.id,
            end);
        return d;

    }

    private ProfessionalGroupRecord groupOf(ContractRecord c)
        throws BusinessException {
        return pgg.findById(c.professionalGroupId)
            .orElseThrow(BusinessException::new);
    }

}
