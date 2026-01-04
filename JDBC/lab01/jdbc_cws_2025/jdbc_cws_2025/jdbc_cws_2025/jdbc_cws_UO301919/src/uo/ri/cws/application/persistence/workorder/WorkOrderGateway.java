package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord>{

	public class WorkOrderRecord {
	    public String id;
	    public double amount;
	    public String state;
	    public long version;
	    public LocalDateTime createdAt;
	    public LocalDateTime updatedAt;
	    public String entitystate;
		public String description;
		public LocalDateTime date;
		public String invoiceId;
		public String mechanicId;
		public String vehicleId;
	}

	public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif);

	public List<WorkOrderRecord> findByMechanic(String id);

	public List<WorkOrderRecord> findByInterventionMechanic(String mechanicId);
}
