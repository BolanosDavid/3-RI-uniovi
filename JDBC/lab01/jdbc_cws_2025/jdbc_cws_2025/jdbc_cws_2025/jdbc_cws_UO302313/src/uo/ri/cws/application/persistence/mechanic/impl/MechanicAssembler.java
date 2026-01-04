package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicAssembler {
    public static Optional<MechanicRecord> toRecord(ResultSet rs)
        throws SQLException {
        MechanicRecord m = new MechanicRecord();
        if (rs.next()) {
            m.id = rs.getString("id");
            m.name = rs.getString("name");
            m.surname = rs.getString("surname");
            m.nif = rs.getString("nif");
            m.version = rs.getLong("version");
            m.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            m.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            m.entityState = rs.getString("entitystate");
            return Optional.ofNullable(m);
        }
        return Optional.empty();
    }

}
