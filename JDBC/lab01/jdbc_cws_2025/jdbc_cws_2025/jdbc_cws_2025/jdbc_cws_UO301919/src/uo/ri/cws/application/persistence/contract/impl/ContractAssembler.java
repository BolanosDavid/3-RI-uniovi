package uo.ri.cws.application.persistence.contract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public class ContractAssembler {
	private static ContractRecord resultSetToContractRecord(ResultSet rs) throws SQLException {
		ContractRecord record = new ContractRecord();
		record.id = rs.getString("id");
		record.version = rs.getLong("version");
		record.dni = rs.getString("nif");
		record.mechanicId = rs.getString("mechanicId");
		record.mechanicName = rs.getString("mechanicName");
		record.mechanicSurname = rs.getString("mechanicSurname");
		record.contractTypeName = rs.getString("contractTypeName");
		record.professionalGroupName = rs.getString("professionalGroupName");
		record.professionalGroupId = rs.getString("professionalGroupId");
		record.annualBaseWage = rs.getDouble("annualBaseSalary");
		record.settlement = rs.getDouble("settlement");
		record.state = rs.getString("state");
		record.startDate = rs.getDate("startDate").toLocalDate();
		if (rs.getDate("endDate") == null) {
			record.endDate = null;
		} else {
			record.endDate = rs.getDate("endDate").toLocalDate();
		}
		return record;
	}
	
	public static List<ContractRecord> toContractList(ResultSet rs) throws SQLException {
		List<ContractRecord> result = new ArrayList<ContractRecord>();
		while (rs.next()) {
			result.add(resultSetToContractRecord(rs));
		}
		return result;
	}
	
	public static Optional<ContractRecord> toContractDALDto(ResultSet rs) throws SQLException {
		Optional<ContractRecord> oir = Optional.empty();
		if (rs.next())
			oir = Optional.of(resultSetToContractRecord(rs));
		return oir;
	}
}
