package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway{

	@Override
	public void add(ContractRecord t) throws PersistenceException {
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ContractRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ContractRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<ContractRecord> findByNif(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ContractRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContractRecord> findByMechanic(String id) {
		try {
			Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TCONTRACTS_FINDBYMECHANIC"))) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    return ContractAssembler.toContractList(rs);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }	
	}

	@Override
	public List<ContractRecord> findInForce() {
		try {
			Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TCONTRACTS_FINDINFORCE"))) {
                try (ResultSet rs = pst.executeQuery()) {
                    return ContractAssembler.toContractList(rs);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }	
	}

	@Override
	public List<ContractRecord> findInForceOrEndingThisMonth(int month, int year) {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TCONTRACTS_FINDINFORCEORENDINGTHISMONTH"))) {
	        	pst.setInt(1, month);
	        	pst.setInt(2, year);
	            try (ResultSet rs = pst.executeQuery()) {
	                return ContractAssembler.toContractList(rs);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public List<ContractRecord> findByProfessionalGroup(String id) {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TCONTRACTS_FINDBYPROFESSIONALGROUP"))) {
	            pst.setString(1, id);
	            try (ResultSet rs = pst.executeQuery()) {
	                return ContractAssembler.toContractList(rs);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	public boolean existsContractsForProfessionalGroup(String groupId) {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TCONTRACTS_EXISTS_FOR_GROUP"))) {
	            pst.setString(1, groupId);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) > 0;
	                }
	                return false;
	            }
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

}
