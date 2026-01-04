package uo.ri.cws.application.service.payroll.crud.commands;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayroll implements Command<Integer> {
    PayrollGateway pg = Factories.persistence.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
        List<PayrollRecord> lastMonthPayrolls = getLastMonthPayrolls();
        int numDeleted = 0;
        for (PayrollRecord p : lastMonthPayrolls) {
            pg.remove(p.id);
            numDeleted++;
        }
        return numDeleted;
    }

    private List<PayrollRecord> getLastMonthPayrolls()
        throws BusinessException {
        YearMonth mesAnterior = YearMonth.now()
            .minusMonths(1);
        LocalDate from = mesAnterior.atDay(1);
        LocalDate to = mesAnterior.plusMonths(1)
            .atDay(1);
        List<PayrollRecord> lastMonthPayrolls =
            pg.findAllPayrollsOfLastMonth(Date.valueOf(from),
                Date.valueOf(to));
        checkValidId(lastMonthPayrolls);
        return lastMonthPayrolls;
    }

    private void checkValidId(List<PayrollRecord> lastMonthPayrolls)
        throws BusinessException {
        boolean hasInvalid = lastMonthPayrolls.stream()
            .map(p -> p.id)
            .map(pg::findById) // Optional<PayrollRecord>
            .anyMatch(Optional::isEmpty);

        if (hasInvalid) {
            throw new BusinessException("receiving invalid id");
        }
    }

}
