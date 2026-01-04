package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public class InvoiceAssembler {

    public static Optional<InvoiceRecord> toRecord(ResultSet rs)
        throws SQLException {
        InvoiceRecord r = new InvoiceRecord();
        if (rs.next()) {
            r.id = rs.getString("id");
            r.number = rs.getLong("number");
            r.date = rs.getDate("date")
                .toLocalDate();
            r.vat = rs.getDouble("vat");
            r.amount = rs.getDouble("amount");
            r.state = rs.getString("state");
            r.version = rs.getLong("version");
            r.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            r.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            r.entityState = rs.getString("entitystate");
            return Optional.ofNullable(r);
        }
        return Optional.empty();
    }

}
