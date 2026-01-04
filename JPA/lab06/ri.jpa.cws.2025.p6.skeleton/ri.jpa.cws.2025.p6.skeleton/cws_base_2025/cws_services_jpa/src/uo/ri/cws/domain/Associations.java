package uo.ri.cws.domain;

import java.util.Optional;

public class Associations {

    public static class Owns {

	public static void link(Client client, Vehicle vehicle) {
	    vehicle._setClient(client);
	    client._getVehicles().add(vehicle);
	}

	public static void unlink(Client cliente, Vehicle vehicle) {
	    cliente._getVehicles().remove(vehicle);
	    vehicle._setClient(null);
	}

    }

    public static class Classifies {

	public static void link(VehicleType vehicleType, Vehicle vehicle) {
	    vehicle._setVehicleType(vehicleType);
	    vehicleType._getVehicles().add(vehicle);
	}

	public static void unlink(VehicleType tipoVehicle, Vehicle vehicle) {
	    tipoVehicle._getVehicles().remove(vehicle);
	    vehicle._setVehicleType(null);
	}
    }

    public static class Holds {

	public static void link(PaymentMean mean, Client client) {
	    client._getPaymentMeans().add(mean);
	    mean._setClient(client);
	}

	public static void unlink(Client client, PaymentMean mean) {

	    client._getPaymentMeans().remove(mean);
	    mean._setClient(null);

	}
    }

    public static class Fixes {

	public static void link(Vehicle vehicle, WorkOrder workOrder) {
	    workOrder._setVehicle(vehicle);
	    vehicle._getWorkOrders().add(workOrder);
	}

	public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
	    vehicle._getWorkOrders().remove(workOrder);
	    workOrder._setVehicle(null);
	}
    }

    public static class Bills {

	public static void link(Invoice invoice, WorkOrder workOrder) {
	    invoice._getWorkOrders().add(workOrder);
	    workOrder._setInvoice(invoice);
	}

	public static void unlink(Invoice invoice, WorkOrder workOrder) {
	    invoice._getWorkOrders().remove(workOrder);
	    workOrder._setInvoice(null);
	}
    }

    public static class Settles {

	public static void link(Invoice invoice, Charge cargo, PaymentMean mp) {
	    cargo._setInvoice(invoice);
	    cargo._setPaymentMean(mp);
	    invoice._getCharges().add(cargo);
	    mp._getCharges().add(cargo);
	}

	public static void unlink(Charge cargo) {
	    cargo.getInvoice()._getCharges().remove(cargo);
	    cargo.getPaymentMean()._getCharges().remove(cargo);
	    cargo._setInvoice(null);
	    cargo._setPaymentMean(null);
	}
    }

    public static class Assigns {

	public static void link(Mechanic mechanic, WorkOrder workOrder) {
	    mechanic._getAssigned().add(workOrder);
	    workOrder._setMechanic(mechanic);
	}

	public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
	    mechanic._getAssigned().remove(workOrder);
	    workOrder._setMechanic(null);
	}
    }

    public static class Intervenes {

	public static void link(WorkOrder w, Intervention i, Mechanic m) {
	    i._setMechanic(m);
	    i._setWorkOrder(w);
	    w._getInterventions().add(i);
	    m._getInterventions().add(i);
	}

	public static void unlink(Intervention i) {
	    i._getMechanic()._getInterventions().remove(i);
	    i._getWorkOrder()._getInterventions().remove(i);
	    i._setMechanic(null);
	    i._setWorkOrder(null);
	}
    }

    public static class Substitutes {
	static void link(SparePart sparePart, Substitution substitution,
			Intervention intervention) {
	    substitution._setSparePart(sparePart);
	    substitution._setIntervention(intervention);

	    sparePart._getSubstitutions().add(substitution);
	    intervention._getSubstitutions().add(substitution);
	}

	public static void unlink(Substitution s) {
	    s.getIntervention()._getSubstitutions().remove(s);
	    s.getSparePart()._getSubstitutions().remove(s);
	    s._setIntervention(null);
	    s._setSparePart(null);
	}
    }

    public static class Bind {
	public static void link(Mechanic mech, Contract c) {
	    Optional<Contract> prevContract =mech.getContractInForce();
            if(prevContract.isPresent()) {
        		prevContract.get().terminate( c.getStartDate().minusDays(1));
            }
	    c._setMechanic(mech);
	    mech._getContracts().add(c);
	}

	public static void unlink(Mechanic mech, Contract c) {
	    mech._getContracts().remove(c);
	    c._setMechanic(null);
	}
    }

    public static class Defines {
	public static void link(ContractType type, Contract c) {
	    c._setContractType(type);
	    type._getContracts().add(c);
	}

	public static void unlink(ContractType type, Contract c) {
	    type._getContracts().remove(c);
	    c._setContractType(null);
	}
    }

    public static class Categorizes {
	public static void link(ProfessionalGroup group, Contract c) {
	    c._setProfessionalGroup(group);
	    group._getContracts().add(c);
	}

	public static void unlink(ProfessionalGroup group, Contract c) {
	    group._getContracts().remove(c);
	    c._setProfessionalGroup(null);
	}
    }

    
    public static class Generates {
        public static void link(Contract c, Payroll p) {
            p._setContract(c);
            c._getPayrolls().add(p);
        }
        public static void unlink(Payroll p) {
            p.getContract()._getPayrolls().remove(p);
            p._setContract(null);
        }
    }
}
