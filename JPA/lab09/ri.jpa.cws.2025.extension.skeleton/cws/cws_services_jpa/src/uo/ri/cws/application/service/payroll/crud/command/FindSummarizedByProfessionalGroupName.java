package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List; 
import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindSummarizedByProfessionalGroupName implements Command<List<PayrollSummaryDto>> {
    private String groupName;
    private PayrollRepository repo = Factories.repository.forPayroll();
   public FindSummarizedByProfessionalGroupName(String groupName) {
       ArgumentChecks.isNotBlank(groupName,
       "FindSummarizedByProfessionalGroupName:: Receiving blank group name");
       ArgumentChecks.isNotEmpty(groupName,
       "FindSummarizedByProfessionalGroupName:: Receiving empty group name");
       this.groupName = groupName;
   }
    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
	Factories.repository.forProfessionalGroup()
		.findByName(groupName)
		.orElseThrow(() -> 
		new BusinessException(
				"FindSummarizedByProfessionalGroupName:: "
				+ "receiving not existing professional group name"));
	List<Payroll> payrolls =repo.findByProfessionalGroupName(groupName);
	
        return DtoAssembler.toSummaryDtoList(payrolls) ;
       
    }
}