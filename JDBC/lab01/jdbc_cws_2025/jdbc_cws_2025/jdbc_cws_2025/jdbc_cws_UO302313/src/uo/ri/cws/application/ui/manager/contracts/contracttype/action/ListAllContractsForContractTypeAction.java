package uo.ri.cws.application.ui.manager.contracts.contracttype.action;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllContractsForContractTypeAction implements Action {

    @Override
    public void execute() throws Exception {
        String typeName =
            Console.readString("Introduzca el nombre del tipo de contrato: ")
                .trim()
                .toUpperCase();
        ContractTypeCrudService ctts =
            Factories.service.forContractTypeCrudService();
        Optional<ContractTypeDto> maybeType = ctts.findByName(typeName);
        if (maybeType.isEmpty()) {
            Console.println("No existe un tipo de contrato con ese nombre.");
            return;
        }
        String typeId = maybeType.get().id;
        ContractCrudService contracts =
            Factories.service.forContractCrudService();
        List<ContractDto> inforce = contracts.findInforceContracts();
        List<ContractDto> filtered = inforce.stream()
            .filter(
                c -> c.contractType != null && Objects.equals(c.contractType.id,
                    typeId))
            .toList();

        if (filtered.isEmpty()) {
            Console.println(
                "No hay trabajadores con contrato en vigor de ese tipo.");
            return;
        }

        MechanicCrudService ms = Factories.service.forMechanicCrudService();

        double totalAnnualBase = 0.0;
        int count = 0;

        Console.println(
            "\nTrabajadores con contrato EN VIGOR para el tipo: " + typeName);
        Console.println(
            "---------------------------------------------------------");

        for (ContractDto c : filtered) {
            totalAnnualBase += c.annualBaseSalary;
            count++;
            Optional<MechanicDto> m = ms.findById(c.mechanic.id);
            Printer.printMechanic(m.get());

        }

        Console.println(
            "---------------------------------------------------------");
        Console.printf("Total trabajadores: %d\n",
            count);
        Console.printf("Acumulado salario base anual: %.2f\n",
            totalAnnualBase);
    }
}
