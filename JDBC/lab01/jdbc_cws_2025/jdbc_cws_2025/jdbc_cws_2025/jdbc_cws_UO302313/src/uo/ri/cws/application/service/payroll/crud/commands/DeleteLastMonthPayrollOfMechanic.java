package uo.ri.cws.application.service.payroll.crud.commands;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollOfMechanic implements Command<Void> {
    private String mechanicId;
    private PayrollGateway pg = Factories.persistence.forPayroll();
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public DeleteLastMonthPayrollOfMechanic(String mechanicId) {
        checkArgumentsValidity(mechanicId);
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        checkMechanicExists();
        YearMonth mesAnterior = YearMonth.now()
            .minusMonths(1);
        LocalDate from = mesAnterior.atDay(1);
        LocalDate to = mesAnterior.plusMonths(1)
            .atDay(1);
        pg.findLastMonthOfMechanic(mechanicId,
            Date.valueOf(from),
            Date.valueOf(to))
            .stream()
            .map(i -> i.id)
            .forEach(pg::remove);

        return null;
    }

    private void checkMechanicExists() throws BusinessException {
        BusinessChecks.exists(mg.findById(mechanicId),
            "Receiving id for a non-existing mechanic");
    }

    private void checkArgumentsValidity(String mechanicId) {
        if (mechanicId == null)
            throw new IllegalArgumentException("Receving null mechanic id");
        if (mechanicId.isEmpty())
            throw new IllegalArgumentException("Receving empty mechanic id");
        if (mechanicId.isBlank())
            throw new IllegalArgumentException("Receving blank mechanic id");
    }
}
