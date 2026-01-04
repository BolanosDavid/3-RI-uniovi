package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.crud.commands.AddProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.commands.DeleteProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.commands.ListProfessionalGroupAll;
import uo.ri.cws.application.service.professionalgroup.crud.commands.ListProfessionalGroupByName;
import uo.ri.cws.application.service.professionalgroup.crud.commands.UpdateProfessionalGroup;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupCrudServiceImpl implements ProfessionalGroupCrudService{
	private CommandExecutor exec = new CommandExecutor();
	
	@Override
	public ProfessionalGroupDto create(ProfessionalGroupDto dto) throws BusinessException {
		return exec.execute(new AddProfessionalGroup(dto));
	}

	@Override
	public void delete(String name) throws BusinessException {
		exec.execute(new DeleteProfessionalGroup(name));
	}

	@Override
	public void update(ProfessionalGroupDto dto) throws BusinessException {
		exec.execute(new UpdateProfessionalGroup(dto));
	}

	@Override
	public Optional<ProfessionalGroupDto> findByName(String name) throws BusinessException {
		return exec.execute(new ListProfessionalGroupByName(name));
	}

	@Override
	public List<ProfessionalGroupDto> findAll() throws BusinessException {
		return exec.execute(new ListProfessionalGroupAll());
	}

}
