package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicAssembler {
	public static Optional<MechanicRecord> toRecord(ResultSet rs) throws SQLException{
		Optional<MechanicRecord> result = Optional.empty();
		if(rs.next()) {
			MechanicRecord m = new MechanicRecord();
	        m.id = rs.getString("id");
	        m.name = rs.getString("name");
	        m.surname = rs.getString("surname");
	        m.version = rs.getLong("version");
	        m.nif = rs.getString("nif");
	        m.createAt = rs.getTimestamp("createdat").toLocalDateTime();
	        m.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
	        m.entitystate = rs.getString("entitystate");
	        result = Optional.of(m);
		}
        return result;
	}
	
	public static MechanicRecord toRecordAll(ResultSet rs) throws SQLException {
	    MechanicRecord m = new MechanicRecord();
	    m.id = rs.getString("id");
	    m.name = rs.getString("name");
	    m.surname = rs.getString("surname");
	    m.version = rs.getLong("version");
	    m.nif = rs.getString("nif");
	    m.createAt = rs.getTimestamp("createdat").toLocalDateTime();
	    m.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
	    m.entitystate = rs.getString("entitystate");
	    return m;
	}
}
