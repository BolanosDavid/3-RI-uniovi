package uo.ri.cws.application.persistence.contract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public class ContractAssembler {

    public static Optional<ContractRecord> toRecord(ResultSet rs)
        throws SQLException {
        ContractRecord c = new ContractRecord();
        c.annualBaseSalary = rs.getDouble("annualbasesalary");
        c.contractTypeId = rs.getString("contracttype_id");
        c.startDate = rs.getDate("startdate");
        c.endDate = rs.getDate("enddate");
        c.mechanicId = rs.getString("mechanic_id");
        c.professionalGroupId = rs.getString("professionalgroup_id");
        c.settlement = rs.getDouble("settlement");
        c.taxRate = rs.getDouble("taxrate");
        c.state = rs.getString("state");
        c.id = rs.getString("id");
        c.createdAt = rs.getTimestamp("createdat")
            .toLocalDateTime();
        c.updatedAt = rs.getTimestamp("updatedat")
            .toLocalDateTime();
        c.version = rs.getLong("version");
        c.entityState = rs.getString("entitystate");
        return Optional.ofNullable(c);
    }

}
