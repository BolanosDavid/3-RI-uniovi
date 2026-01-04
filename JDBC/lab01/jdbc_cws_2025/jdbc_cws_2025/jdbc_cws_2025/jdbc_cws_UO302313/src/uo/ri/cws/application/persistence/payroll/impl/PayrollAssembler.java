package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollAssembler {

    public static Optional<PayrollRecord> toRecord(ResultSet rs)
        throws SQLException {

        PayrollRecord p = new PayrollRecord();
        p.date = rs.getDate("date")
            .toLocalDate();
        p.baseSalary = rs.getDouble("basesalary");
        p.extraSalary = rs.getDouble("extrasalary");
        p.nicDeduction = rs.getDouble("nicdeduction");
        p.taxDeduction = rs.getDouble("taxdeduction");
        p.trienniumEarning = rs.getDouble("trienniumearning");
        p.productivityEarning = rs.getDouble("productivityearning");
        p.contractId = rs.getString("contract_id");
        p.id = rs.getString("id");
        p.createdAt = rs.getTimestamp("createdat")
            .toLocalDateTime();
        p.updatedAt = rs.getTimestamp("updatedat")
            .toLocalDateTime();
        p.version = rs.getLong("version");
        p.entityState = rs.getString("entitystate");
        return Optional.ofNullable(p);
    }

}
