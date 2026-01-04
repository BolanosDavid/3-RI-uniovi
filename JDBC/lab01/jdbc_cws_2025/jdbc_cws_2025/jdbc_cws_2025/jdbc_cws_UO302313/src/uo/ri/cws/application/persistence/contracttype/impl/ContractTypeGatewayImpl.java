package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

    @Override
    public void add(ContractTypeRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TCONTRACTSTYPE_INSERT");
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setString(1, t.id);
            pst.setLong(2, t.version);
            pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pst.setString(5, t.entityState);
            pst.setDouble(6, t.compensationDaysPerYear);
            pst.setString(7, t.name);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TCONTRACTSTYPE_DELETE");
        try (PreparedStatement pst = c.prepareStatement(query);) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void update(ContractTypeRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TCONTRACTSTYPE_UPDATE");
        try (PreparedStatement pst = c.prepareStatement(query)) {
            pst.setDouble(1, t.compensationDaysPerYear);
            pst.setString(2, t.entityState);
            pst.setLong(3, t.version + 1L);
            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pst.setString(5, t.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Optional<ContractTypeRecord> findById(String id)
        throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();

        String query = "TCONTRACTTYPES_FIND_BY_ID";

        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractTypeAssembler.toRecord(rs);

            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ContractTypeRecord> findAll() throws PersistenceException {
        List<ContractTypeRecord> lista = new ArrayList<>();
        Connection c = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TMECHANICS_FIND_ALL");
        try (PreparedStatement pst = c.prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {
            Optional<ContractTypeRecord> record;
            do {
                record = ContractTypeAssembler.toRecord(rs);
                record.ifPresent(lista::add);
            } while (record.isPresent());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return lista;
    }

    @Override
    public Optional<ContractTypeRecord> findByName(String name) {
        Connection c = Jdbc.getCurrentConnection();

        String query = "TCONTRACTSTYPE_FIND_BY_NAME";
        try (PreparedStatement pst =
            c.prepareStatement(Queries.getSQLSentence(query))) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                return ContractTypeAssembler.toRecord(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
