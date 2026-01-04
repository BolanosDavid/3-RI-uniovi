package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.command.FindContractById;
import uo.ri.cws.application.service.contract.crud.command.FindInforceContract;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.NotYetImplementedException;

public class ContractCrudServiceImpl implements ContractCrudService{
    private CommandExecutor executor =  Factories.executor.forExecutor();
    @Override
    public ContractDto create(ContractDto c) throws BusinessException {
	throw new NotYetImplementedException();
    }

    @Override
    public void update(ContractDto dto) throws BusinessException {
	throw new NotYetImplementedException();
    }

    @Override
    public void delete(String id) throws BusinessException {
	throw new NotYetImplementedException();
    }

    @Override
    public void terminate(String contractId) throws BusinessException {
	throw new NotYetImplementedException();
    }

    @Override
    public Optional<ContractDto> findById(String id) throws BusinessException {
	return executor.execute(new FindContractById(id));
    }

    @Override
    public List<ContractSummaryDto> findByMechanicNif(String nif)
		    throws BusinessException {
	throw new NotYetImplementedException();
    }

    @Override
    public List<ContractDto> findInforceContracts() throws BusinessException {
	return executor.execute(new FindInforceContract());
    }

    @Override
    public List<ContractSummaryDto> findAll() throws BusinessException {
	throw new NotYetImplementedException();
    }

}
