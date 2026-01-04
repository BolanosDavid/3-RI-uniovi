package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(WorkOrderRecord w) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TWORKORDERS_UPDATE");
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1, w.invoiceId);
            pst.setString(2, w.state);
            pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setString(4, w.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<WorkOrderRecord> findById(String id)
        throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_ID_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return WorkOrderAssembler.toRecord(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        List<WorkOrderRecord> out = new ArrayList<>();
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TWORKORDERS_FIND_ALL");
        try (PreparedStatement pst = c.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            Optional<WorkOrderRecord> rec;
            do {
                rec = WorkOrderAssembler.toRecord(rs);
                rec.ifPresent(out::add);
            } while (rec.isPresent());

            return out;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String mechanicId) {
        List<WorkOrderRecord> out = new ArrayList<>();
        Connection c = Jdbc.getCurrentConnection();
        String query = "TWORKORDERS_FIND_BY_MECHANICID";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, mechanicId);
            ResultSet rs = pst.executeQuery();
            Optional<WorkOrderRecord> rec;
            do {
                rec = WorkOrderAssembler.toRecord(rs);
                rec.ifPresent(out::add);
            } while (rec.isPresent());

            return out;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<WorkOrderRecord>
            findByInterventionMechanicId(String mechanicId) {
        List<WorkOrderRecord> out = new ArrayList<>();
        Connection c = Jdbc.getCurrentConnection();
        String query = "TWORKORDERS_FIND_BY_INTERVENTION_M_ID";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, mechanicId);
            ResultSet rs = pst.executeQuery();
            Optional<WorkOrderRecord> rec;
            do {
                rec = WorkOrderAssembler.toRecord(rs);
                rec.ifPresent(out::add);
            } while (rec.isPresent());

            return out;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
