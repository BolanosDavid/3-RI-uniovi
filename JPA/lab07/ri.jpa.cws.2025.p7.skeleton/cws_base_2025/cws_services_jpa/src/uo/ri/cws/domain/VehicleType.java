package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class VehicleType extends BaseEntity {
    // natural attributes
    @Column(unique = true) private String name;
    private double pricePerHour;
    // accidental attributes
    @OneToMany(mappedBy = "vehicleType") private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleType() {}
    public VehicleType(String name, double pricePerHour,
		    Set<Vehicle> vehicles) {
	ArgumentChecks.isNotBlank(name, "Vehicle Type:: not valid name");
	ArgumentChecks.isTrue(pricePerHour >= 0,
			"Vehicle Type:: not valid price per hour");

	this.name = name;
	this.pricePerHour = pricePerHour;
    }

    public VehicleType(String name, double pricePerHour) {
	this(name, pricePerHour, null);
    }

    public VehicleType(String name) {
	this(name,0,null);
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

   

}
