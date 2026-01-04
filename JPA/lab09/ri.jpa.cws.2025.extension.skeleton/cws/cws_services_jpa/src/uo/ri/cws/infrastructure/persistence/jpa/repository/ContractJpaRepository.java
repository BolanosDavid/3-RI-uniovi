package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.util.exception.NotYetImplementedException;

public class ContractJpaRepository 
	extends BaseJpaRepository<Contract> 
	implements ContractRepository {


	@Override
	public List<Contract> findAllInForce() {
	    String query = "Contract.findAllInForce";
		return Jpa.getManager()
				.createNamedQuery(query,Contract.class)
				.getResultList();
	}

	@Override
	public List<Contract> findByMechanicId(String id) {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<Contract> findByProfessionalGroupId(String id) {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<Contract> findByContractTypeId(String id) {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<Contract> findAllInForceThisMonth(LocalDate present) {
	    LocalDate previousMonth = present.minusMonths(1);
	    LocalDate start = previousMonth
			    .with(TemporalAdjusters.firstDayOfMonth());
	    LocalDate end   = previousMonth
			    .with(TemporalAdjusters.lastDayOfMonth());
	    String query = "Contract.findAllInForceThisMonth";
	    return Jpa.getManager()
				.createNamedQuery(query,Contract.class)
				.setParameter(1, start)
				.setParameter(2, end)
				.getResultList();
	}

	@Override
	public List<Contract> findInforceContracts() {
	    String query = "Contract.findInforceContracts";
		return Jpa.getManager()
				.createNamedQuery(query,Contract.class)
				.getResultList();
	}

}
