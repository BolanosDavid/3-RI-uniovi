package uo.ri.cws.application.persistence.professionalgroup;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupRecord>{

	public class ProfessionalGroupRecord {
		public String id;
	    public LocalDateTime createdAt;
	    public String entityState;
	    public String name;
	    public Double productivityRate;
	    public Double trienniumPayment;
	    public LocalDateTime updatedAt;
	    public Long version;
	}

	public Optional<ProfessionalGroupRecord> findByName(String name);

}
