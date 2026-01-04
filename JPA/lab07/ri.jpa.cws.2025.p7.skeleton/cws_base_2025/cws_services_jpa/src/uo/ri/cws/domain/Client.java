package uo.ri.cws.domain;

import java.util.HashSet; 
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity()
@Table(name = "TCLIENTS")
public class Client extends BaseEntity {
    // Atributos naturales
    @Column(unique= true) private String nif; // clave natural
    private String name;
    private String surname;
    private String email;
    private String phone;
    @Embedded private Address address;
    // Atributos accidentales
    @OneToMany(mappedBy = "client") private Set<Vehicle> vehicles = new HashSet<Vehicle>();
    @OneToMany(mappedBy ="client") private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();
    Client(){}	// Para JPA
    public Client(String nif, String name, String surname, String email,
		    String phone, Address address) {
	ArgumentChecks.isNotBlank(nif, "Client:: not acceptble nif");
	ArgumentChecks.isNotBlank(name, "Client:: not acceptble name");
	ArgumentChecks.isNotBlank(surname, "Client:: not acceptble surname");
	ArgumentChecks.isNotBlank(email, "Client:: not acceptble email");
	ArgumentChecks.isNotBlank(phone, "Client:: not acceptble phone");

	this.nif = nif;
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.phone = phone;
	this.address = address;
    }

    public Client(String nif, String name, String surname) {
	this(nif, name, surname, "no@email", "no-phone", null);
    }

    public Client(String nif) {
	this(nif, "noname", "nosurname", "no@email", "no-phone", null);
    }
    public String getNif() {
	return nif;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<Vehicle>(this.vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return this.vehicles;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<PaymentMean>(this.paymentMeans);
    }

    Set<PaymentMean> _getPaymentMeans() {
	return this.paymentMeans;
    }
    public void setAddress(Address address2) {
	this.address = address2;
	
    }
    @Override
    public String toString() {
	return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname
			+ ", email=" + email + ", phone=" + phone + ", address="
			+ address + "]";
    }
    


}
