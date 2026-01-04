package uo.ri.cws.application.persistence.contracttype;

import java.util.Optional;

import uo.ri.cws.application.persistence.BasicRecord;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord> {

    public Optional<ContractTypeRecord> findByName(String name);

    public class ContractTypeRecord extends BasicRecord {
        public Double compensationDaysPerYear;
        public String name;

    }
}
