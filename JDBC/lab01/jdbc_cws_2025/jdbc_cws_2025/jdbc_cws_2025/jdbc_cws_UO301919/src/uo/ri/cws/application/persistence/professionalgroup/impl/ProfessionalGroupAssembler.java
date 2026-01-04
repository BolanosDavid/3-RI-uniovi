package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupAssembler {
	
	public static ProfessionalGroupRecord resultSetToProfessionalRecord(ResultSet rs) throws SQLException {
		ProfessionalGroupRecord record = new ProfessionalGroupRecord();
        record.id = rs.getString("ID");
        record.name = rs.getString("NAME");
        record.trienniumPayment = rs.getDouble("TRIENNIUMPAYMENT");
        record.productivityRate = rs.getDouble("PRODUCTIVITYRATE");
        record.createdAt = rs.getTimestamp("CREATEDAT").toLocalDateTime();
        record.updatedAt = rs.getTimestamp("UPDATEDAT").toLocalDateTime();
        record.entityState = rs.getString("ENTITYSTATE");
        record.version = rs.getLong("VERSION");
        return record;
	}

	public static Optional<ProfessionalGroupRecord> toProfessionalGroupRecord(ResultSet rs) throws SQLException {
	    if (rs.next()) {
	        return Optional.of(resultSetToProfessionalRecord(rs));
	    }
	    return Optional.empty();
	}


}
