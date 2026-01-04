package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway{

	@Override
	public void add(WorkOrderRecord t) throws PersistenceException {
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkOrderRecord t) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE"));
	        pst.setString(1, t.state);
	        pst.setString(2, t.invoiceId);
	        pst.setLong(3, t.version);
	        pst.setTimestamp(4, java.sql.Timestamp.valueOf(t.updatedAt));
	        pst.setString(5, t.entitystate);
	        pst.setString(6, t.id);
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


	@Override
	public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
		try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"));
	        pst.setString(1, id);
	        try (ResultSet rs = pst.executeQuery()) {
                return WorkOrderAssembler.toWorkOrderRecord(rs);
            }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public Optional<WorkOrderRecord> findByNif(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<WorkOrderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
		List<WorkOrderRecord> result = new ArrayList<>();
		try {
			Connection c = Jdbc.getCurrentConnection();
            PreparedStatement pst = c.prepareStatement(
            		Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"));
            pst.setString(1, nif);
            ResultSet rs = pst.executeQuery();
            result = WorkOrderAssembler.toWorkOrderRecordList(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		return result;
	}

	@Override
	public List<WorkOrderRecord> findByMechanic(String id) {
		List<WorkOrderRecord> result = new ArrayList<>();
		try {
			Connection c = Jdbc.getCurrentConnection();
            PreparedStatement pst = c.prepareStatement(
            		Queries.getSQLSentence("TWORKORDERS_FINDBYMECHANIC"));
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            result = WorkOrderAssembler.toWorkOrderRecordList(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		return result;
	}

	@Override
	public List<WorkOrderRecord> findByInterventionMechanic(String id) {
		List<WorkOrderRecord> result = new ArrayList<>();
		try {
			Connection c = Jdbc.getCurrentConnection();
            PreparedStatement pst = c.prepareStatement(
            		Queries.getSQLSentence("TWORKORDERS_FIND_BY_INTERVENTION_MECHANIC_ID"));
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            result = WorkOrderAssembler.toWorkOrderRecordList(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		return result;
	}

}
