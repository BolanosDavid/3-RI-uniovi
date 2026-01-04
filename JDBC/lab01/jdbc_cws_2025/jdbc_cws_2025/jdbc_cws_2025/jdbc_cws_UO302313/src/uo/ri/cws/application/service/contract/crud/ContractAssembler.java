package uo.ri.cws.application.service.contract.crud;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.MechanicOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;

public class ContractAssembler {
    public static ContractDto toDto(ContractRecord r) {
        ContractDto d = new ContractDto();
        d.id = r.id;
        d.version = r.version;
        d.startDate = r.startDate.toLocalDate();
        d.endDate = r.endDate == null ? null : r.endDate.toLocalDate();
        d.annualBaseSalary = r.annualBaseSalary;
        d.taxRate = r.taxRate;
        d.settlement = r.settlement;
        d.state = r.state;
        d.mechanic = new MechanicOfContractDto();
        d.mechanic.id = r.mechanicId;
        d.contractType = new ContractTypeOfContractDto();
        d.contractType.id = r.contractTypeId;
        d.professionalGroup = new ProfessionalGroupOfContractDto();
        d.professionalGroup.id = r.professionalGroupId;
        return d;
    }
}
