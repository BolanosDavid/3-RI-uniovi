package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository 
	extends BaseJpaRepository<Payroll> 
	implements PayrollRepository {

	@Override
	public List<Payroll> findByContract(String contractId) {
	    String query = "Payroll.findByContract";
	    return Jpa.getManager()
			    .createNamedQuery(query,Payroll.class)
			    .setParameter(1, contractId).getResultList();
	}

	@Override
	public List<Payroll> findLastMonthPayrolls() {
	    LocalDate now = LocalDate.now();
	    LocalDate previousMonth = now.minusMonths(1);
	    LocalDate start = previousMonth
			    .with(TemporalAdjusters.firstDayOfMonth());
	    LocalDate end   = previousMonth
			    .with(TemporalAdjusters.lastDayOfMonth());
	    return Jpa.getManager()
		            .createNamedQuery("Payroll.findLastMonthPayrolls"
		                              , Payroll.class)
		            .setParameter(1, start)
		            .setParameter(2, end)
		            .getResultList();
			
				
	}

	@Override
	public Optional<Payroll> findLastPayrollByMechanicId(String mechanicId) {
	    LocalDate now = LocalDate.now();
	    LocalDate previousMonth = now.minusMonths(1);
	    LocalDate start = previousMonth
			    .with(TemporalAdjusters.firstDayOfMonth());
	    LocalDate end   = previousMonth
			    .with(TemporalAdjusters.lastDayOfMonth());
	    String jpql = "Payroll.findLastPayrollByMechanicId";
	    return Jpa.getManager()
				.createNamedQuery(jpql,Payroll.class)
				.setParameter(1, mechanicId)
				.setParameter(2, start)
				.setParameter(3,end)
				.getResultStream()
				.findFirst();	
	}

	@Override
	public List<Payroll> findByProfessionalGroupName(String name) {
	    String query = "Payroll.findByProfessionalGroupName";
	    return Jpa.getManager()
				.createNamedQuery(query,Payroll.class )
				.setParameter(1, name)
				.getResultList();
	}

	@Override
	public List<Payroll> findByMechanicId(String mId) {
	    String query = "Payroll.findByMechanicId";
		return Jpa.getManager()
				.createNamedQuery(query,Payroll.class )
				.setParameter(1, mId)
				.getResultList();
	}

	@Override
	public Optional<Payroll> findByContractIdAndDate(String id,
	                                                 	LocalDate date) {
	    String query = "Payroll.findByContractIdAndDate";
	    return Jpa.getManager()
				.createNamedQuery(query,Payroll.class)
				.setParameter(1, id)
				.setParameter(2,date)
				.getResultStream()
				.findFirst();
	}

}
