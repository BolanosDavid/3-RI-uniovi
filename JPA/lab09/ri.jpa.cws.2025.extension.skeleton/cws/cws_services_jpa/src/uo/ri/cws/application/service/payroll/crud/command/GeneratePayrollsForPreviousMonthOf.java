package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrollsForPreviousMonthOf
        implements Command<List<PayrollDto>> {

    private ContractRepository cRepo =
            Factories.repository.forContract();
    private PayrollRepository pRepo =
            Factories.repository.forPayroll();

    private final LocalDate present;

    public GeneratePayrollsForPreviousMonthOf(LocalDate present) {
        if (present == null) {
            throw new IllegalArgumentException("present cannot be null");
        }
        this.present = present;
    }
    public GeneratePayrollsForPreviousMonthOf() {
	present = LocalDate.now();
    }

    @Override
    public List<PayrollDto> execute() throws BusinessException {
        YearMonth target = YearMonth.from(present).minusMonths(1);
        LocalDate start = target.atDay(1);
        LocalDate end   = target.atEndOfMonth();

        List<PayrollDto> result = new ArrayList<>();
        List<Contract> contracts = cRepo.findAllInForceThisMonth(present);

        for (Contract c : contracts) {
            if (!overlapsMonth(c, start, end)) {
                continue; 
            }
            if (alreadyHasPayroll(c, end)) {
                continue;
            }
            Payroll payroll = new Payroll(c, end);
            pRepo.add(payroll);
            result.add(DtoAssembler.toDto(payroll));
        }

        return result;
    }

    /**
     * Comprueba si el contrato estuvo activo en un periodo determinado
     * @param c:Contract contrato a comprobar
     * @param start:LocalDate fecha inicial
     * @param end:LocalDate fecha final
     * @return true si el contrato estuvo activo false de lo contrario
     */
    private boolean overlapsMonth(Contract c, LocalDate start, LocalDate end) {
	    LocalDate contractStart = c.getStartDate();
	    LocalDate contractEnd   = c.getEndDate(); 

	    if (contractStart.isAfter(end)) {
	        return false; 
	    }
	    if (contractEnd != null && contractEnd.isBefore(start)
		                && c.isTerminated()) {
		            return false;
		        }
	    return true;
	}

    /**
     * Comprueba si ya existe una nómina para ese contrato en un tiempo determinado
     * @param c: Contract Contrato a comprobar
     * @param date: LocalDate Fecha(mes) a comprobar
     * @return true si ya tiene false en caso contrario
     */
    private boolean alreadyHasPayroll(Contract c, LocalDate date) {
        return c.getPayrolls().stream()
                .anyMatch(p -> p.getDate().equals(date));
    }
}
