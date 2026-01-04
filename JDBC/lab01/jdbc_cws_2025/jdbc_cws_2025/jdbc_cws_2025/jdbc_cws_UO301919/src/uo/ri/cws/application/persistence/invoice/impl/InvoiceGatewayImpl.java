package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway{

	@Override
	public void add(InvoiceRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
	            pst.setString(1, t.id);
	            pst.setLong(2, t.number);
	            pst.setDate(3, java.sql.Date.valueOf(t.date));
	            pst.setDouble(4, t.vat);
	            pst.setDouble(5, t.amount);
	            pst.setString(6, t.state);
	            pst.setLong(7, t.version);
	            pst.setTimestamp(8, Timestamp.valueOf(t.createdat));
	            pst.setTimestamp(9, Timestamp.valueOf(t.updatedat));
	            pst.setString(10, t.entityState);
	            pst.executeUpdate();
			}
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void remove(String id) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_DELETE"));
	        pst.setString(1, id);
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public void update(InvoiceRecord t) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_UPDATE"));
	        pst.setLong(1, t.number);
	        pst.setDate(2, java.sql.Date.valueOf(t.date));
	        pst.setDouble(3, t.vat);
	        pst.setDouble(4, t.amount);
	        pst.setString(5, t.state);
	        pst.setLong(6, t.version);
	        pst.setTimestamp(7, Timestamp.valueOf(t.createdat));
	        pst.setTimestamp(8, Timestamp.valueOf(t.updatedat));
	        pst.setString(9, t.entityState);
	        pst.setString(10, t.id);
	        pst.executeUpdate();
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


	@Override
	public Optional<InvoiceRecord> findById(String id) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_FIND_BY_ID"));
	        pst.setString(1, id);
	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            InvoiceRecord ir = new InvoiceRecord();
	            ir.id = rs.getString("id");
	            ir.number = rs.getLong("number");
	            ir.date = rs.getDate("date").toLocalDate();
	            ir.vat = rs.getDouble("vat");
	            ir.amount = rs.getDouble("amount");
	            ir.state = rs.getString("state");
	            ir.version = rs.getLong("version");
	            ir.createdat = rs.getTimestamp("createdat").toLocalDateTime();
	            ir.updatedat = rs.getTimestamp("updatedat").toLocalDateTime();
	            ir.entityState = rs.getString("entityState");
	            return Optional.of(ir);
	        } else {
	            return Optional.empty();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


	@Override
	public Optional<InvoiceRecord> findByNif(String nif) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_FIND_BY_NIF"));
	        pst.setString(1, nif);
	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            InvoiceRecord ir = new InvoiceRecord();
	            ir.id = rs.getString("id");
	            ir.number = rs.getLong("number");
	            ir.date = rs.getDate("date").toLocalDate();
	            ir.vat = rs.getDouble("vat");
	            ir.amount = rs.getDouble("amount");
	            ir.state = rs.getString("state");
	            ir.version = rs.getLong("version");
	            ir.createdat = rs.getTimestamp("createdat").toLocalDateTime();
	            ir.updatedat = rs.getTimestamp("updatedat").toLocalDateTime();
	            ir.entityState = rs.getString("entityState");
	            return Optional.of(ir);
	        } else {
	            return Optional.empty();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


	@Override
	public List<InvoiceRecord> findAll() throws PersistenceException {
		List<InvoiceRecord> result = new ArrayList<>();
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_FIND_ALL"));
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            InvoiceRecord ir = new InvoiceRecord();
	            ir.id = rs.getString("id");
	            ir.number = rs.getLong("number");
	            ir.date = rs.getDate("date").toLocalDate();
	            ir.vat = rs.getDouble("vat");
	            ir.amount = rs.getDouble("amount");
	            ir.state = rs.getString("state");
	            ir.version = rs.getLong("version");
	            ir.createdat = rs.getTimestamp("createdat").toLocalDateTime();
	            ir.updatedat = rs.getTimestamp("updatedat").toLocalDateTime();
	            ir.entityState = rs.getString("entityState");
	            result.add(ir);
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	    return result;
	}

	@Override
	public long findNextNumber() {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"));
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            return rs.getLong(1) + 1;
	        } else {
	            return 1;
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


}
