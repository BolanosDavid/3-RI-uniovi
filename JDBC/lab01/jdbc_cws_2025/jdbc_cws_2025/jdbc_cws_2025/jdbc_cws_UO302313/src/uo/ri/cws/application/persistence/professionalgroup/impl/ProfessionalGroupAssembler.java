package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupAssembler {

    public static Optional<ProfessionalGroupRecord> toRecord(ResultSet rs)
        throws SQLException {

        if (rs.next()) {
            ProfessionalGroupRecord p = new ProfessionalGroupRecord();
            p.productivityRate = rs.getDouble("productivityrate");
            p.trienniumPayment = rs.getDouble("trienniumpayment");
            p.name = rs.getString("name");

            p.id = rs.getString("id");
            p.version = rs.getLong("version");
            p.createdAt = rs.getTimestamp("createdat")
                .toLocalDateTime();
            p.updatedAt = rs.getTimestamp("updatedat")
                .toLocalDateTime();
            p.entityState = rs.getString("entitystate");
            return Optional.of(p);
        }
        return Optional.empty();
    }

}
