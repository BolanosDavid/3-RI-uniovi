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

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
        Connection connection = Jdbc.getCurrentConnection();
        String query = Queries.getSQLSentence("TINVOICES_INSERT");
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, t.id);
            pst.setLong(2, t.number);
            pst.setDate(3, java.sql.Date.valueOf(t.date));
            pst.setDouble(4, t.vat);
            pst.setDouble(5, t.amount);
            pst.setString(6, "NOT_YET_PAID");
            pst.setLong(7, 1L);
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            pst.setString(10, "ENABLED");
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
        throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<InvoicingWorkOrderRecord>
            findNotInvoicedWorkOrdersByClientNif(String nif) {
        List<InvoicingWorkOrderRecord> lista =
            new ArrayList<InvoicingWorkOrderRecord>();
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(
                Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
            pst.setString(1, nif);
            try (ResultSet rs = pst.executeQuery();) {
                Optional<InvoicingWorkOrderRecord> record;
                do {
                    record = InvoicingWorkOrderAssembler.toRecord(rs);
                    record.ifPresent(lista::add);
                } while (record.isPresent());
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long findNextNumber() {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection.prepareStatement(
                Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1) + 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1L;
    }
}
