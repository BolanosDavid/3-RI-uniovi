package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class WorkOrderJpaRepository
		extends BaseJpaRepository<WorkOrder>
		implements WorkOrderRepository {

	@Override
	public List<WorkOrder> findByIds(List<String> idsAveria) {
	    String query = "WorkOrder.findByIds";
		return Jpa.getManager()
				.createNamedQuery(query, WorkOrder.class)
				.setParameter( 1, idsAveria )
				.getResultList();
		}

	@Override
	public List<WorkOrder> findByClientNif(String nif) {
	    String query  = "WorkOrder.findByClientNif";
		return Jpa.getManager()
				.createNamedQuery(query,WorkOrder.class)
				.setParameter(1, nif)
				.getResultList();
	}

	@Override
	public List<WorkOrder> findNotInvoicedByClientNif(String nif) {
	    String query = "WorkOrder.findNotInvoicedByClientNif";
		return Jpa.getManager()
				.createNamedQuery(query,WorkOrder.class)
				.setParameter(1, nif)
				.getResultList();
	}

	@Override
	public List<WorkOrder> findByPlateNumber(String plate) {
	    String query = "WorkOrder.findByPlateNumber";
		return Jpa.getManager()
				.createNamedQuery(query,WorkOrder.class)
				.setParameter(1, plate)
				.getResultList();
	}

}
