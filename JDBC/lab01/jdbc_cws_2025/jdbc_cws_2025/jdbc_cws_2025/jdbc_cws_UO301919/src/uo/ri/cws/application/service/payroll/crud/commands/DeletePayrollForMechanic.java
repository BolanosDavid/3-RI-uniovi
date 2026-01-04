package uo.ri.cws.application.service.payroll.crud.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class DeletePayrollForMechanic implements Command<Void> {

	private String id;
	
	public DeletePayrollForMechanic(String id) {
		ArgumentChecks.isNotBlank(id, "The mechanic id cannot be blank");
		this.id = id;
	}



	@Override
	public Void execute() throws BusinessException {
	    Optional<MechanicRecord> mechanic = Factories.persistence.forMechanic().findById(id);
	    if (mechanic.isEmpty()) {
	        throw new BusinessException("The mechanic to delete does not exist");
	    }

	    List<ContractRecord> contracts = Factories.persistence.forContract().findByMechanic(mechanic.get().id);
	    List<String> contractIds = new ArrayList<>();
	    for (ContractRecord c : contracts) {
	        contractIds.add(c.id);
	    }

	    List<PayrollRecord> payrolls = Factories.persistence.forPayroll().findByContractsIdsInDates(
	        contractIds, LocalDate.now().minusMonths(1).getMonthValue(), LocalDate.now().getYear());

	    for (PayrollRecord p : payrolls) {
	        Factories.persistence.forPayroll().remove(p.id);
	    }

	    return null;
	}


}
