package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollAssembler {
	
	public static PayrollRecord toRecord(ResultSet rs) throws SQLException {
	    PayrollRecord record = new PayrollRecord();
	    record.id = rs.getString("id");
		record.version = rs.getLong("version");
		record.contractId = rs.getString("contract_id");
		record.date = rs.getDate("date").toLocalDate();
		record.monthlyWage = rs.getDouble("baseSalary");
		record.bonus = rs.getDouble("extraSalary");
		record.productivityBonus = rs.getDouble("productivityEarning");
		record.trienniumPayment = rs.getDouble("trienniumEarning");
		record.incomeTax = rs.getDouble("taxDeduction");
		record.nic = rs.getDouble("nicDeduction");
		record.createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
        record.updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
	    return record;
	}
	
	public static List<PayrollRecord> toPayrollRecordList(ResultSet rs) throws SQLException {
	    List<PayrollRecord> list = new ArrayList<>();
	    while (rs.next()) {
	        list.add(toRecord(rs));
	    }
	    return list;
	}
	
	public static Optional<PayrollRecord> toOptionalRecord(ResultSet rs) throws SQLException {
		Optional<PayrollRecord> oir = Optional.empty();
		if (rs.next())
			oir = Optional.of(toRecord(rs));
		return oir;
	}


}
