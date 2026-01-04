package uo.ri.cws.domain;

import java.util.HashSet; 
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
 
public class Vehicle extends BaseEntity {
    private String plateNumber;
    private String make;
    private String model;

    private Client client;
    private VehicleType vehicleType;
    private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

    Vehicle() {
    }

    public Vehicle(
		   String plateNumber, String make, String model,
		   Client client) {
	ArgumentChecks.isNotBlank(plateNumber,
				  "Vehicle:: not acceptable plateNumber");
	ArgumentChecks.isNotBlank(make,
				  "Vehicle:: not acceptable make");
	ArgumentChecks.isNotBlank(model,
				  "Vehicle:: not acceptable model");
	this.plateNumber = plateNumber;
	this.make = make;
	this.model = model;
    }

    public Vehicle(
		   String plateNumber, String make, String model) {
	this(plateNumber, make, model, null);
    }

    public Vehicle(
		   String plateNumber) {
	this(plateNumber, "no-makel", "no-model", null);
    }

    public String
	   getPlateNumber() {
	return plateNumber;
    }

    public String
	   getMake() {
	return make;
    }

    public String
	   getModel() {
	return model;
    }

    public Client
	   getClient() {
	return this.client;
    }

    public VehicleType
	   getVehicleType() {
	return this.vehicleType;
    }

    void _setClient(Client client) {
	this.client = client;
    }

    void _setVehicleType(VehicleType v) {
	this.vehicleType = v;

    }

    public Set<WorkOrder>
	   getWorkOrders() {
	return new HashSet<WorkOrder>(this.workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return this.workOrders;
    }


}
