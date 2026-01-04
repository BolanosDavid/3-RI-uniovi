package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway{

	@Override
	public void add(ProfessionalGroupRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPROFESSIONALGROUPS_ADD"))) {
	        	pst.setString(1, t.id);
                pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
	        	pst.setString(3, t.entityState);
	        	pst.setString(4, t.name);
	        	pst.setDouble(5, t.productivityRate);
	        	pst.setDouble(6, t.trienniumPayment);
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
	        	pst.setLong(8, t.version);               
	            pst.executeUpdate();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}
	
	@Override
	public void remove(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPROFESSIONALGROUPS_DELETE"))) {
	        	pst.setString(1, id);             
	            pst.executeUpdate();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public void update(ProfessionalGroupRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPROFESSIONALGROUPS_UPDATE"))) {
	        	pst.setDouble(1, t.trienniumPayment);    
	        	pst.setDouble(2, t.productivityRate);  
                pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	        	pst.setLong(4, t.version + 1);
	        	pst.setString(5, t.id);
	            pst.executeUpdate();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public Optional<ProfessionalGroupRecord> findById(String id) throws PersistenceException {
	    try {
	    	Connection con = Jdbc.getCurrentConnection();
	    	try (PreparedStatement pst = con.prepareStatement(
		            Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYID"))) {   
		        pst.setString(1, id);
		        try (ResultSet rs = pst.executeQuery()) {
		            return ProfessionalGroupAssembler.toProfessionalGroupRecord(rs);
		        }
		    }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public Optional<ProfessionalGroupRecord> findByNif(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
		List<ProfessionalGroupRecord> result = new ArrayList<>();
		try {
			Connection con = Jdbc.getCurrentConnection();
	    	try (PreparedStatement pst = con.prepareStatement(
		            Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDALL"))) {
	    		ResultSet rs = pst.executeQuery();
	    		while(rs.next()) {
			        result.add(ProfessionalGroupAssembler.resultSetToProfessionalRecord(rs));
	    		}
		    }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
		return result;
	}

	@Override
	public Optional<ProfessionalGroupRecord> findByName(String name) {
		try {
	    	Connection con = Jdbc.getCurrentConnection();
	    	try (PreparedStatement pst = con.prepareStatement(
		            Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYNAME"))) {
	    		pst.setString(1, name);
		        try (ResultSet rs = pst.executeQuery()) {
		            return ProfessionalGroupAssembler.toProfessionalGroupRecord(rs);
		        }
		    }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

}
