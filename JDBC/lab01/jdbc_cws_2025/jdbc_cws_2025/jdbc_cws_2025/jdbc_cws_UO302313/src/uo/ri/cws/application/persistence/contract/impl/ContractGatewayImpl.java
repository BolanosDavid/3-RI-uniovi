package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

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
    public Optional<ContractRecord> findById(String id)
        throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TCONTRACTS_FIND_BY_ID");
        try (PreparedStatement pst = c.prepareStatement(query);) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return ContractAssembler.toRecord(rs);
                }
                return Optional.empty();

            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ContractRecord> findActiveAt(Date from) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TCONTRACTS_FIND_ACTIVE_AT";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setDate(1, from);
            pst.setDate(2, from);
            try (ResultSet rs = pst.executeQuery()) {
                List<ContractRecord> out = new ArrayList<ContractRecord>();
                while (rs.next()) {
                    Optional<ContractRecord> r = ContractAssembler.toRecord(rs);
                    if (r.isPresent())
                        out.add(r.get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findTerminatedBetween(Date from, Date to) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TCONTRACTS_FIND_TERMINATED_BETWEEN";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setDate(1, from);
            pst.setDate(2, to);
            try (ResultSet rs = pst.executeQuery()) {
                List<ContractRecord> out = new ArrayList<ContractRecord>();
                while (rs.next()) {
                    out.add(ContractAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findByContractTypeId(String id) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TCONTRACTS_FIND_BY_CONTRACTTYPE";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                List<ContractRecord> out = new ArrayList<ContractRecord>();
                while (rs.next()) {
                    Optional<ContractRecord> r = ContractAssembler.toRecord(rs);
                    if (r.isPresent())
                        out.add(r.get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findContractForMechanic(String mechanicId) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TCONTRACTS_FIND_CONTRACTED_MECHANIC";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, mechanicId);
            try (ResultSet rs = pst.executeQuery()) {
                List<ContractRecord> out = new ArrayList<ContractRecord>();
                while (rs.next()) {
                    Optional<ContractRecord> r = ContractAssembler.toRecord(rs);
                    if (r.isPresent())
                        out.add(r.get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractRecord> findContractedMechanics() {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TCONTRACTS_FIND_CONTRACTED_MECHANICS";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            try (ResultSet rs = pst.executeQuery()) {
                List<ContractRecord> out = new ArrayList<ContractRecord>();
                while (rs.next()) {
                    Optional<ContractRecord> r = ContractAssembler.toRecord(rs);
                    if (r.isPresent())
                        out.add(r.get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
