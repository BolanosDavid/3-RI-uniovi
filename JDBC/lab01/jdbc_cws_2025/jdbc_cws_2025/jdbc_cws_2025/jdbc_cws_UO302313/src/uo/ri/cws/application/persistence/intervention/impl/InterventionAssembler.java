package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;;

public class InterventionAssembler {
    public static Optional<InterventionRecord> toRecord(ResultSet rs)
        throws SQLException {
        InterventionRecord m = new InterventionRecord();
        if (rs.next()) {
            m.id = rs.getString("id");
            m.minutes = rs.getInt("minutes");
            m.date = rs.getTimestamp("date")
                .toLocalDateTime();
            m.version = rs.getLong("version");
            m.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            m.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            m.entityState = rs.getString("entitystate");
            m.mechanicId = rs.getString("mechanic_id");
            m.workOrderId = rs.getString("workorder_id");
            return Optional.ofNullable(m);
        }
        return Optional.empty();

    }

}
