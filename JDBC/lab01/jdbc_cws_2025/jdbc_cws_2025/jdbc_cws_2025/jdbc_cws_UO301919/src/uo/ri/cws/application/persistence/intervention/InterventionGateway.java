package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord>{

	public class InterventionRecord {
		public String id;
		public long version;
		public LocalDate date;
		public int minutes;
		public String mechanicId;
		public String workOrderId;
	}
	
	/**
	 * Método fundByMechanic
	 * @param id
	 * @return
	 */
	List<InterventionRecord> findByMechanic(String id);

}
