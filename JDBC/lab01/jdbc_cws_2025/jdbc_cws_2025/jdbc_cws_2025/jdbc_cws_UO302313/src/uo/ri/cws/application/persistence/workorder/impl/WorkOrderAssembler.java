package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderAssembler {
    public static Optional<WorkOrderRecord> toRecord(ResultSet rs)
        throws SQLException {
        WorkOrderRecord m = new WorkOrderRecord();
        if (rs.next()) {
            m.id = rs.getString("id");
            m.amount = rs.getDouble("amount");
            m.date = rs.getTimestamp("date")
                .toLocalDateTime();
            m.state = rs.getString("state");
            m.mechanicId = rs.getString("mechanic_id");
            m.invoiceId = rs.getString("invoice_id");
            m.vehicleId = rs.getString("vehicle_id");
            m.version = rs.getLong("version");
            m.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            m.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            m.description = rs.getString("description");
            m.entityState = rs.getString("entitystate");
            return Optional.ofNullable(m);
        }
        return Optional.empty();

    }

}
