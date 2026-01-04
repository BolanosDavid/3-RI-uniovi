package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class ProfessionalGroupAssembler {

	public static ProfessionalGroupDto toDto(ProfessionalGroupRecord rec) {
		ProfessionalGroupDto dto = new ProfessionalGroupDto();
		dto.id = rec.id;
		dto.version = rec.version;
		dto.name = rec.name;
		dto.productivityRate = rec.productivityRate;
		dto.trienniumPayment = rec.trienniumPayment;
		return dto;
	}
	
	public static ProfessionalGroupRecord toRecord(ProfessionalGroupDto dto) {
		ProfessionalGroupRecord rec = new ProfessionalGroupRecord();
		rec.id = dto.id;
		rec.version = dto.version;
		rec.name = dto.name;
		rec.productivityRate = dto.productivityRate;
		rec.trienniumPayment = dto.trienniumPayment;
		return rec;
	}
	
	public static Optional<ProfessionalGroupDto> toDto(Optional<ProfessionalGroupRecord> rec){
		Optional<ProfessionalGroupDto> dto = rec.isEmpty() ? Optional.ofNullable(null) 
				: Optional.ofNullable(toDto(rec.get()));
		return dto;
	}
	
	public static List<ProfessionalGroupDto> toDtoList(List<ProfessionalGroupRecord> recList){
		List<ProfessionalGroupDto> result = new ArrayList<>();
		for (ProfessionalGroupRecord pr: recList) {
			result.add(toDto(pr));
		}
		return result;
	}
}
