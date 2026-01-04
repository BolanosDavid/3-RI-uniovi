package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord>{

	public class ContractRecord {
	    public String id;
	    public long version;
	    public String mechanicId;
	    public String dni;
	    public String mechanicName;
	    public String mechanicSurname;
	    public String contractTypeName;
	    public String professionalGroupName;
	    public String professionalGroupId;
	    public LocalDate startDate;
	    public LocalDate endDate;
	    public double annualBaseWage;
	    public double settlement;
	    public String state;
	}

	List<ContractRecord> findByMechanic(String id);

	List<ContractRecord> findInForce();

	List<ContractRecord> findInForceOrEndingThisMonth(int monthValue, int year);

	List<ContractRecord> findByProfessionalGroup(String id);
	
	boolean existsContractsForProfessionalGroup(String id);

}
