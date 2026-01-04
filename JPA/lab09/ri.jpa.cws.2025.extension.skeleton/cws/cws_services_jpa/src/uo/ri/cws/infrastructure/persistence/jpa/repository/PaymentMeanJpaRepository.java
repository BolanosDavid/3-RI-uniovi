package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List; 

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.util.exception.NotYetImplementedException;

public class PaymentMeanJpaRepository
		extends BaseJpaRepository<PaymentMean>
		implements PaymentMeanRepository {

	@Override
	public List<PaymentMean> findPaymentMeansByClientId(Long id) {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<PaymentMean> findPaymentMeansByClientNif(String nif) {
	    throw new NotYetImplementedException();
	}

}
