package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List; 
import java.util.Optional;

import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.util.exception.NotYetImplementedException;

public class SparePartJpaRepository
		extends BaseJpaRepository<SparePart>
		implements SparePartRepository {

	@Override
	public Optional<SparePart> findByCode(String code) {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<SparePart> findUnderStockNotPending() {
	    throw new NotYetImplementedException();
	}

	@Override
	public List<SparePart> findByDescription(String description) {
	    throw new NotYetImplementedException();
	}

}
