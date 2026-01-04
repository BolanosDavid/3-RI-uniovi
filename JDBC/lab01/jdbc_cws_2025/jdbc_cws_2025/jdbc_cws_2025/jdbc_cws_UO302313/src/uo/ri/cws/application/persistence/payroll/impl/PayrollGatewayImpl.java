package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.Date;
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

public class PayrollGatewayImpl implements PayrollGateway {

    @Override
    public void add(PayrollRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TPAYROLLS_INSERT");
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1,
                t.id);
            pst.setDouble(2,
                t.baseSalary);
            pst.setDouble(3,
                t.extraSalary);
            pst.setDouble(4,
                t.trienniumEarning);
            pst.setDouble(5,
                t.productivityEarning);
            pst.setDouble(6,
                t.taxDeduction);
            pst.setDouble(7,
                t.nicDeduction);
            pst.setDate(8,
                java.sql.Date.valueOf(t.date));
            pst.setString(9,
                t.contractId);
            pst.setLong(10,
                t.version);
            pst.setTimestamp(11,
                new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(12,
                new Timestamp(System.currentTimeMillis()));
            pst.setString(13,
                t.entityState);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TPAYROLLS_DELETE");
        try (PreparedStatement pst = c.prepareStatement(query);) {
            pst.setString(1,
                id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
        throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TPAYROLLS_FIND_BY_ID");
        try (PreparedStatement pst = c.prepareStatement(query);) {
            pst.setString(1,
                id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return PayrollAssembler.toRecord(rs);
                }
                return Optional.empty();

            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TPAYROLLS_FIND_ALL");
        try (PreparedStatement pst = c.prepareStatement(query);) {
            try (ResultSet rs = pst.executeQuery()) {
                List<PayrollRecord> out = new ArrayList<PayrollRecord>();
                while (rs.next()) {
                    out.add(PayrollAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PayrollRecord> findAllForMechanic(String mechanicId)
        throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TPAYROLLS_FIND_ALL_FOR_MECHANIC";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query));) {
            pst.setString(1,
                mechanicId);
            try (ResultSet rs = pst.executeQuery()) {
                List<PayrollRecord> out = new ArrayList<PayrollRecord>();
                while (rs.next()) {
                    out.add(PayrollAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<PayrollRecord> findAllPayrollsOfLastMonth(Date from,
                                                          Date to) {
        Connection c = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = c.prepareStatement(
            Queries.getSQLSentence("TPAYROLLS_FIND_LAST_MONTH"))) {
            pst.setDate(1,
                from);
            pst.setDate(2,
                to);
            try (ResultSet rs = pst.executeQuery()) {
                List<PayrollRecord> out = new ArrayList<PayrollRecord>();
                while (rs.next()) {
                    out.add(PayrollAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PayrollRecord> findLastMonthOfMechanic(String mechanicId,
                                                       Date from,
                                                       Date to) {
        Connection c = Jdbc.getCurrentConnection();

        String query = "TPAYROLLS_LAST_MONTH_MECHANIC";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1,
                mechanicId);
            pst.setDate(2,
                from);
            pst.setDate(3,
                to);
            try (ResultSet rs = pst.executeQuery()) {
                List<PayrollRecord> out = new ArrayList<PayrollRecord>();
                while (rs.next()) {
                    out.add(PayrollAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PayrollRecord> findAllByProfGroupName(String name) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TPAYROLLS_FIND_BY_PROFESSIONALGROUPS";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1,
                name);
            try (ResultSet rs = pst.executeQuery()) {
                List<PayrollRecord> out = new ArrayList<PayrollRecord>();
                while (rs.next()) {
                    out.add(PayrollAssembler.toRecord(rs)
                        .get());
                }
                return out;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<PayrollRecord> findContractBetween(String contractId,
                                                       Date from,
                                                       Date to) {
        Connection c = Jdbc.getCurrentConnection();
        String query = "TPAYROLLS_FIND_CONTRACT_BETWEEN";

        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1,
                contractId);
            pst.setDate(2,
                from);
            pst.setDate(3,
                to);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return PayrollAssembler.toRecord(rs);
                }
                return Optional.empty();

            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
