package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord>{

	public class PayrollRecord {
		public String id;
    	public long version;
    	public String contractId;
    	public LocalDate date;
		public double monthlyWage;
		public double bonus;
		public double productivityBonus;
		public double trienniumPayment;
		public LocalDateTime createdAt;
		public LocalDateTime updatedAt;
		public double incomeTax;
		public double nic;
		public double netWage;
	}

	public List<PayrollRecord> findInDates(int monthValue, int year);

	public List<PayrollRecord> findByContractsIdsInDates(List<String> contractIds, int month, int year);

	public List<PayrollRecord> findByContractsIds(List<String> contractIds);

}
