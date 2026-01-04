package uo.ri.cws.application.service.workOrder;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class WorkOrderAssembler {
	public static InvoicingWorkOrderDto toDto(WorkOrderRecord r) {
        InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
        dto.id = r.id;
        dto.description = r.description;
        dto.date = r.date;
        dto.state = r.state;
        dto.amount = r.amount;
        return dto;
    }
}
