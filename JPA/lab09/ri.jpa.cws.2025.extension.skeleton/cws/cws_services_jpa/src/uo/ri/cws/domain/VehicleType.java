package uo.ri.cws.domain;

import java.util.HashSet; 
import java.util.Set;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType extends BaseEntity {
    // natural attributes
    private String name;
    private double pricePerHour;
    // accidental attributes
    private Set<Vehicle> vehicles = new HashSet<>();

    VehicleType() {
    }

    public VehicleType(
		       String name, double pricePerHour,
		       Set<Vehicle> vehicles) {
	ArgumentChecks.isNotBlank(name,
				  "Vehicle Type:: not valid name");
	ArgumentChecks.isTrue(pricePerHour >= 0,
			      "Vehicle Type:: not valid price per hour");

	this.name = name;
	this.pricePerHour = pricePerHour;
    }

    public VehicleType(
		       String name, double pricePerHour) {
	this(name, pricePerHour, null);
    }

    public VehicleType(
		       String name) {
	this(name, 0, null);
    }


    public String
	   getName() {
	return name;
    }

    public double
	   getPricePerHour() {
	return pricePerHour;
    }

    public Set<Vehicle>
	   getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

}
