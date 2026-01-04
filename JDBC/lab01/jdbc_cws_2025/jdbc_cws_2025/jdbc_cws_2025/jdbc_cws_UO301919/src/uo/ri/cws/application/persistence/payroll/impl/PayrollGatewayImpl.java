package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway{

	@Override
	public void add(PayrollRecord r) throws PersistenceException {
		try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TPAYROLLS_ADD"))) {

	            pst.setString(1, r.id);
	            pst.setLong(2, r.version);
	            pst.setString(3, r.contractId);
	            pst.setDate(4, java.sql.Date.valueOf(r.date));

	            pst.setDouble(5, r.monthlyWage);
	            pst.setDouble(6, r.bonus);
	            pst.setDouble(7, r.productivityBonus);
	            pst.setDouble(8, r.trienniumPayment);
	            pst.setDouble(9, r.incomeTax);
	            pst.setDouble(10, r.nic);
	            pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));

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
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TPAYROLLS_DELETEBYID"))) {
	            pst.setString(1, id);
	            pst.executeUpdate();
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


	@Override
	public void update(PayrollRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PayrollRecord> findById(String id) throws PersistenceException {
		try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TPAYROLLS_FINDBYID"))) {
	            pst.setString(1, id);
	            try (ResultSet rs = pst.executeQuery()) {
	                return PayrollAssembler.toOptionalRecord(rs);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public Optional<PayrollRecord> findByNif(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PayrollRecord> findAll() throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TPAYROLLS_FINDALL"));
	             ResultSet rs = pst.executeQuery()) {
	            return PayrollAssembler.toPayrollRecordList(rs);
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public List<PayrollRecord> findInDates(int monthValue, int year) {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.getSQLSentence("TPAYROLLS_FINDINDATES"))) {
	            
	            pst.setInt(1, monthValue);
	            pst.setInt(2, year);
	            
	            try (ResultSet rs = pst.executeQuery()) {
	                return PayrollAssembler.toPayrollRecordList(rs);
	            }
	        }
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	@Override
	public List<PayrollRecord> findByContractsIdsInDates(List<String> contractIds, int month, int year) {
		List<PayrollRecord> payrolls = new ArrayList<PayrollRecord>();
		try {
			Connection c = Jdbc.getCurrentConnection(); 
	        	for (String id : contractIds) {
	        	    try (PreparedStatement pst = c.prepareStatement(
	        	            Queries.getSQLSentence("TPAYROLLS_FINDBYCONTRACTIDSINDATES"))) {
	        	        pst.setString(1, id);
	        	        pst.setInt(2, month);
	        	        pst.setInt(3, year);
	        	        try (ResultSet rs = pst.executeQuery()) {
	        	            List<PayrollRecord> auxList = PayrollAssembler.toPayrollRecordList(rs);
	        	            payrolls.addAll(auxList);
	        	        }
	        	    }
	        	}
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
		return payrolls;
	}

	@Override
	public List<PayrollRecord> findByContractsIds(List<String> contractIds) {
		List<PayrollRecord> payrolls = new ArrayList<>();
		try {
			Connection c = Jdbc.getCurrentConnection(); 
	        	for (String id : contractIds) {
	        	    try (PreparedStatement pst = c.prepareStatement(
	        	            Queries.getSQLSentence("TPAYROLLS_FINDBYCONTRACTIDS"))) {
	        	        pst.setString(1, id);
	        	        try (ResultSet rs = pst.executeQuery()) {
	        	            payrolls.addAll(PayrollAssembler.toPayrollRecordList(rs));
	        	        }
	        	    }
	        	}
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
		return payrolls;
	}


}
