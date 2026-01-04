package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Client {
    // Atributos naturales
    private String nif; // clave natural
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;
    // Atributos accidentales
    private Set<Vehicle> vehicles = new HashSet<Vehicle>();
    private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();

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

    @Override
    public String toString() {
	return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname
			+ ", email=" + email + ", phone=" + phone + ", address="
			+ address + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(nif);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Client other = (Client) obj;
	return Objects.equals(nif, other.nif);
    }

}
