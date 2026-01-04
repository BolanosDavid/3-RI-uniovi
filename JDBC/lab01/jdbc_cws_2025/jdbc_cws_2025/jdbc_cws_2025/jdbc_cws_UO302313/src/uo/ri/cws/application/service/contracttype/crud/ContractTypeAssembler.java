package uo.ri.cws.application.service.contracttype.crud;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class ContractTypeAssembler {
    public static ContractTypeDto toDto(ContractTypeRecord r) {
        ContractTypeDto d = new ContractTypeDto();
        d.id = r.id;
        d.compensationDays = r.compensationDaysPerYear;
        d.name = r.name;
        d.version = r.version;
        return d;
    }

    public static ContractTypeRecord toRecord(ContractTypeDto p) {
        ContractTypeRecord r = new ContractTypeRecord();
        r.id = p.id;
        r.compensationDaysPerYear = p.compensationDays;
        r.version = p.version;
        r.name = p.name;
        return r;
    }
}
