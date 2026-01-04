package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastGeneratedOfMechanicId implements Command<Void> {
    private PayrollRepository repo = Factories.repository.forPayroll();
    private MechanicRepository mRepo = Factories.repository.forMechanic();
    private String mechanicId;

    public DeleteLastGeneratedOfMechanicId(String mechanicId) {
	ArgumentChecks.isNotEmpty(mechanicId,
			"DeleteLastGeneratedOfMechanicId:: Receiving empty mechanic id");
	ArgumentChecks.isNotBlank(mechanicId,
			"DeleteLastGeneratedOfMechanicId:: Receiving blank mechanic id");

	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	BusinessChecks.exists(mRepo.findById(mechanicId),
			"DeleteLastGeneratedOfMechanicId:: no mechanic found with that id");
	Optional<Payroll> payroll = repo.findLastPayrollByMechanicId(mechanicId);
	payroll.ifPresent(repo::remove);
			
	return null;

    }
}