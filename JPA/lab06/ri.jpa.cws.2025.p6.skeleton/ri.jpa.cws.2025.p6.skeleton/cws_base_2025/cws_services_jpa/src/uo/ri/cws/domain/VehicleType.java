package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType {
    // natural attributes
    private String name;
    private double pricePerHour;
    // accidental attributes
    private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleType(String name, double pricePerHour,
		    Set<Vehicle> vehicles) {
	ArgumentChecks.isNotBlank(name, "Vehicle Type:: not valid name");
	ArgumentChecks.isTrue(pricePerHour > 0,
			"Vehicle Type:: not valid price per hour");

	this.name = name;
	this.pricePerHour = pricePerHour;
    }

    public VehicleType(String name, double pricePerHour) {
	this(name, pricePerHour, null);
    }

    @Override
    public String toString() {
	return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
			+ ", vehicles=" + vehicles + "]";
    }

    public String getName() {
	return name;
    }

    public double getPricePerHour() {
	return pricePerHour;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    @Override
    public int hashCode() {
	return Objects.hash(name, pricePerHour, vehicles);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	VehicleType other = (VehicleType) obj;
	return Objects.equals(name, other.name)
			&& Double.doubleToLongBits(pricePerHour) == Double
					.doubleToLongBits(other.pricePerHour)
			&& Objects.equals(vehicles, other.vehicles);
    }

}
