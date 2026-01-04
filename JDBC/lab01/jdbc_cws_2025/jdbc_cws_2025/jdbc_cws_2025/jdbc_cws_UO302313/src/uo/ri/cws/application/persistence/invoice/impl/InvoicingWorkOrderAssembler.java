package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;

public class InvoicingWorkOrderAssembler {
    public static Optional<InvoicingWorkOrderRecord> toRecord(ResultSet rs)
        throws SQLException {
        InvoicingWorkOrderRecord r = new InvoicingWorkOrderRecord();
        if (rs.next()) {
            r.id = rs.getString("id");
            r.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            r.description = rs.getString("description");
            r.date = rs.getTimestamp("date")
                .toLocalDateTime();
            r.state = rs.getString("state");
            r.amount = rs.getDouble("amount");
            return Optional.ofNullable(r);
        }
        return Optional.empty();
    }

}
