package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class InterventionAssembler {
	
	/**
	 * Método que devuelve un InterventionRecord a partir de un resultSet
	 * @param rs a convertir
	 * @return record
	 * @throws SQLException
	 */
	private static InterventionRecord resultSetToInterventionDto(ResultSet rs) throws SQLException {
		InterventionRecord record = new InterventionRecord();
		record.id = rs.getString("id");
		record.version = rs.getLong("version");
		record.date = rs.getDate("date").toLocalDate();
		record.minutes = rs.getInt("minutes");
		record.mechanicId = rs.getString("mechanic_id");
		record.workOrderId = rs.getString("workOrder_id");		
		return record;
	}

	/**
	 * Método que devuelve una lista de InterventionRecord a partir de un resultSet
	 * @param rs a convertir
	 * @return lista de record
	 * @throws SQLException
	 */
	public static List<InterventionRecord> toInterventionList(ResultSet rs) throws SQLException {
		List<InterventionRecord> result = new ArrayList<InterventionRecord>();
		while (rs.next()) {
			result.add(resultSetToInterventionDto(rs));
		}
		return result;
	}

	/**
	 * Método que devuelve un InterventionRecord o un vacío a partir de un resultSet
	 * @param rs a convertir
	 * @return record o vacío
	 * @throws SQLException
	 */
	public static Optional<InterventionRecord> toInterventionDALDto(ResultSet rs) throws SQLException {
		Optional<InterventionRecord> oir = Optional.empty();
		if (rs.next())
			oir = Optional.of(resultSetToInterventionDto(rs));
		return oir;
	}
}
