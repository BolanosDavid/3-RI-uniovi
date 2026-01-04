package uo.ri.cws.application.service.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
public class ContractAssembler {
	public static ContractDto toDto(ContractRecord r) {
		ContractDto dto = new ContractDto();
		dto.id = r.id;
		dto.mechanic.id = r.mechanicId;
		dto.mechanic.nif = r.dni;
		dto.mechanic.name = r.mechanicName;
		dto.mechanic.surname = r.mechanicSurname;
		dto.version = r.version;
		dto.startDate = r.startDate;
		dto.endDate = r.endDate;
		dto.annualBaseSalary = r.annualBaseWage;
		dto.settlement = r.settlement;
		dto.state = r.state;	
		return dto;
	}
	
	public static Optional<ContractDto> toBLDto(Optional<ContractRecord> arg) {
		Optional<ContractDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toDto(arg.get()));
		return result;
	}
	
	public static List<ContractDto> toDtoList(List<ContractRecord> arg) {
		List<ContractDto> result = new ArrayList<ContractDto>();
		for (ContractRecord mr : arg)
			result.add(toDto(mr));
		return result;
	}
}
