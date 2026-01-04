package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderAssembler {
	
	private static WorkOrderRecord resultSetToWorkOrderRecord(ResultSet rs) throws SQLException {
    	WorkOrderRecord wor = new WorkOrderRecord();
    	wor.id = rs.getString("id");
    	wor.version = rs.getLong("version");
    	wor.vehicleId = rs.getString("vehicle_id");
        wor.description = rs.getString("description");
        wor.date = rs.getTimestamp("date").toLocalDateTime();
        wor.state = rs.getString("state");
        wor.amount = rs.getDouble("amount");
    	wor.mechanicId = rs.getString("mechanic_id");
    	wor.invoiceId = rs.getString("invoice_id");
        return wor;
    }
	
   public static Optional<WorkOrderRecord> toWorkOrderRecord(ResultSet rs) throws SQLException{
    	WorkOrderRecord wor = null;
    	if(rs.next()) {
    		wor = resultSetToWorkOrderRecord(rs);
    	}
    	return Optional.ofNullable(wor);
    }
   
   public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
		List<WorkOrderRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToWorkOrderRecord(rs));
		}

		return res;
	}
}
