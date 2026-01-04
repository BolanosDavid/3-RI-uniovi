package uo.ri.cws.application.service.payroll.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractAssembler;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.PayrollAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListFindSummarizedByProfessionalName implements Command<List<PayrollSummaryDto>> {
	
	private String name;
	
	
	
	public ListFindSummarizedByProfessionalName(String name) {
		ArgumentChecks.isNotBlank(name, "The professional group name can not be blanck");
		this.name = name;
	}

	@Override
	public List<PayrollSummaryDto> execute() throws BusinessException {
		List<String> contractIds = new ArrayList<>();
		List<PayrollSummaryDto> payrollSummary = new ArrayList<>();
		List<PayrollDto> payrolls;
		List<ContractDto> contracts;
		Optional<ProfessionalGroupRecord> professionalGroup;
		professionalGroup = Factories.persistence.forProfessionalGroup().findByName(name);
		if(professionalGroup.isEmpty()) {
			throw new BusinessException("The professional group does not exist");
		}
		contracts = ContractAssembler.toDtoList(Factories.persistence.forContract().findByProfessionalGroup(professionalGroup.get().id));
		for (ContractDto c: contracts) {
			contractIds.add(c.id);
		}
		payrolls = PayrollAssembler.toDtoList(Factories.persistence.forPayroll().findByContractsIds(contractIds));
		for(PayrollDto p: payrolls) {
			payrollSummary.add(PayrollAssembler.toSummaryDto(p));
		}
		return payrollSummary;
	}

}
