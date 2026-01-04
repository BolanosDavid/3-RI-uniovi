package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListFindSummarizedByMechanic implements Command<List<PayrollSummaryDto>> {

	private String id;
	
	
	
	public ListFindSummarizedByMechanic(String id) {
		ArgumentChecks.isNotBlank(id, "The id cannot be blank");
		this.id = id;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		List<String> contractIds = new ArrayList<>();
		List<PayrollSummaryDto> payrollsSummary = new ArrayList<>();
		List<PayrollDto> payrolls;
		List<ContractRecord> contracts;
		Optional<MechanicRecord> mechanic;
		mechanic = Factories.persistence.forMechanic().findById(id);
		if(mechanic.isEmpty()) {
			throw new BusinessException("The mechanic to list does not exist");
		}
		contracts = Factories.persistence.forContract().findByMechanic(mechanic.get().id);
		for (ContractRecord c : contracts) {
		    contractIds.add(c.id);
		}

		payrolls = PayrollAssembler.toDtoList(Factories.persistence.forPayroll().findByContractsIds(contractIds));
		for(PayrollDto p: payrolls) {
			payrollsSummary.add(PayrollAssembler.toSummaryDto(p));
		}
		return payrollsSummary;
	}

}
