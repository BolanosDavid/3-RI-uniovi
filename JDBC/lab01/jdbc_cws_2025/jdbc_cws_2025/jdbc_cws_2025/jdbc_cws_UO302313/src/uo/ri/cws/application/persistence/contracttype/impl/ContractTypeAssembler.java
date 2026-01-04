package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public class ContractTypeAssembler {

    public static Optional<ContractTypeRecord> toRecord(ResultSet rs)
        throws SQLException {
        if (rs.next()) {
            ContractTypeRecord c = new ContractTypeRecord();
            c.compensationDaysPerYear = rs.getDouble("compensationdaysperyear");
            c.name = rs.getString("name");
            c.id = rs.getString("id");
            c.version = rs.getLong("version");
            c.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            c.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            c.entityState = rs.getString("entitystate");
            return Optional.of(c);
        }
        return Optional.empty();
    }

}
