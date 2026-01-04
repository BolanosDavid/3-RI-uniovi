package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.FindMechanicsWithContractInForce;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicById;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	private CommandExecutor exec = new CommandExecutor();

	@Override
	public MechanicDto create(MechanicDto dto) throws BusinessException {
		return exec.execute(new AddMechanic(dto));
	}

	@Override
	public void delete(String mechanicId) throws BusinessException {
		exec.execute(new DeleteMechanic(mechanicId));

	}

	@Override
	public void update(MechanicDto dto) throws BusinessException {
		exec.execute(new UpdateMechanic(dto));
	}
	
	@Override
	public Optional<MechanicDto> findById(String id) throws BusinessException {
		return exec.execute(new ListMechanicById(id));
	}

	@Override
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		return exec.execute(new ListMechanicByNif(nif));
	}

	@Override
	public List<MechanicDto> findAll() throws BusinessException {
		return exec.execute(new ListAllMechanics());
	}

	@Override
	public List<MechanicDto> findMechanicsInForce() throws BusinessException {
		return exec.execute(new FindMechanicsWithContractInForce());
	}

}
