package uo.ri.ui.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

/**
 * Adapter que redirige la salida de System.out a un String
 * para poder usar Printer en interfaces gráficas Swing
 */
public class SwingPrinterAdapter {
    
    /**
     * Captura toda la salida de System.out durante la ejecución de una operación
     * y la devuelve como String
     */
    private static String captureOutput(Runnable printOperation) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8.name());
            System.setOut(ps);
            printOperation.run();
            return baos.toString(StandardCharsets.UTF_8.name());
            
        } catch (UnsupportedEncodingException e) {
            return baos.toString();
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    
    public static String formatPayrolls(List<PayrollDto> payrolls) {
        return captureOutput(() -> Printer.printPayrolls(payrolls));
    }
    
    public static String formatPayrollDetails(PayrollDto dto) {
        return captureOutput(() -> Printer.printPayrollDetails(dto));
    }
    
    public static String formatPayrollSummary(PayrollSummaryDto dto) {
        return captureOutput(() -> Printer.printPayrollSummary(dto));
    }
    
    public static String formatContractDetails(ContractDto contract) {
        return captureOutput(() -> Printer.printContractDetails(contract));
    }
    
    public static String formatSummarizedContract(ContractDto contract) {
        return captureOutput(() -> Printer.printSumarizedContract(contract));
    }
    
    public static String formatContracts(List<ContractDto> contracts) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of contracts\n");
        sb.append("=================\n\n");
        
        for (ContractDto c : contracts) {
            sb.append(captureOutput(() -> Printer.printSumarizedContract(c)));
        }
        
        return sb.toString();
    }
    
    public static String formatSummarizedContracts(List<ContractDto> contracts) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of contracts\n");
        sb.append("=================\n\n");
        
        for (ContractDto c : contracts) {
            sb.append(captureOutput(() -> Printer.printSumarizedContract(c)));
        }
        
        return sb.toString();
    }
    
    
    public static String formatMechanic(MechanicDto mechanic) {
        return captureOutput(() -> Printer.printMechanic(mechanic));
    }
    
    public static String formatMechanics(List<MechanicDto> mechanics) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of mechanics\n");
        sb.append("=================\n\n");
        
        for (MechanicDto m : mechanics) {
            sb.append(formatMechanic(m));
            sb.append("-------------------\n");
        }
        
        return sb.toString();
    }
    public static String formatContractType(ContractTypeDto contractType) {
        return captureOutput(() -> Printer.printContractType(contractType));
    }
    
    public static String formatContractTypes(List<ContractTypeDto> contractTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of contract types\n");
        sb.append("======================\n\n");
        
        for (ContractTypeDto ct : contractTypes) {
            sb.append(formatContractType(ct));
            sb.append("-------------------\n");
        }
        
        return sb.toString();
    }
    
    public static String formatProfessionalGroup(ProfessionalGroupDto group) {
        return captureOutput(() -> Printer.printProfessionalGroup(group));
    }
    public static String formatProfessionalGroups(List<ProfessionalGroupDto> groups) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of professional groups\n");
        sb.append("===========================\n\n");
        
        for (ProfessionalGroupDto pg : groups) {
            sb.append(formatProfessionalGroup(pg));
            sb.append("-------------------\n");
        }
        
        return sb.toString();
    }
    public static String formatVehicleType(VehicleTypeDto vehicleType) {
        return captureOutput(() -> Printer.printVehicleType(vehicleType));
    }
    
    public static String formatVehicleTypes(List<VehicleTypeDto> vehicleTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of vehicle types\n");
        sb.append("=====================\n\n");
        
        for (VehicleTypeDto vt : vehicleTypes) {
            sb.append(formatVehicleType(vt));
            sb.append("-------------------\n");
        }
        
        return sb.toString();
    }
    
    public static String formatWorkOrder(WorkOrderDto workOrder) {
        return captureOutput(() -> Printer.printWorkOrder(workOrder));
    }
    
    public static String formatWorkOrders(List<WorkOrderDto> workOrders) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of work orders\n");
        sb.append("===================\n\n");
        
        for (WorkOrderDto wo : workOrders) {
            sb.append(formatWorkOrder(wo));
        }
        
        return sb.toString();
    }
    
    
    public static String formatInvoice(InvoiceDto invoice) {
        return captureOutput(() -> Printer.printInvoice(invoice));
    }
    
    public static String formatPaymentMeans(List<PaymentMeanDto> paymentMeans) {
        return captureOutput(() -> Printer.printPaymentMeans(paymentMeans));
    }
}
