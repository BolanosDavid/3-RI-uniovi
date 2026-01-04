package uo.ri.ui.contract.view.contracttypes;

import java.awt.*;
import java.util.Optional;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class UpdateContractTypePanel extends BasePanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    private JTextField nameField;
    private JTextField compensationDaysField;
    private JButton updateButton;
    private ContractTypeDto currentContractType;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Update contract type"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel idLabel = new JLabel("Contract type ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        idField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(idField, gbc);
        JButton searchButton = ComponentFactory.createPrimaryButton("Search");
        searchButton.addActionListener(e -> handleSearch());
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 0, 16, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(searchButton, gbc);
        JLabel nameLabel = new JLabel("Contract type name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameLabel, gbc);
        nameField = ComponentFactory.createStyledTextField();
        nameField.setEnabled(false);
        gbc.gridy = 4;
        formPanel.add(nameField, gbc);
        JLabel compensationLabel = new JLabel("Compensation days:");
        compensationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 5;
        formPanel.add(compensationLabel, gbc);
        compensationDaysField = ComponentFactory.createStyledTextField();
        compensationDaysField.setEnabled(false);
        gbc.gridy = 6;
        formPanel.add(compensationDaysField, gbc);
        updateButton = ComponentFactory.createSuccessButton("Update Contract Type");
        updateButton.setEnabled(false);
        updateButton.addActionListener(e -> handleUpdate());
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(updateButton, gbc);
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(Box.createVerticalGlue(), gbc);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleSearch() {
        try {
            String name = idField.getText().trim();
            ContractTypeCrudService service = Factories.service
        		    				     .forContractTypeCrudService();
            Optional<ContractTypeDto> result = service.findByName(name);
            
            if (!result.isPresent()) {
                throw new BusinessException(
                                     "Contract type not found with name: " + name);
            }
            
            currentContractType = result.get();
            nameField.setText(currentContractType.name);
            compensationDaysField.setText(String
                                   .valueOf(currentContractType.compensationDays));
            
            nameField.setEnabled(true);
            compensationDaysField.setEnabled(true);
            updateButton.setEnabled(true);
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        } catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    private void handleUpdate() {
        try {
            if (currentContractType == null) {
                throw new BusinessException(
                                          "Please search for a contract type first");
            }
            
            currentContractType.name = nameField.getText().trim();
            currentContractType.compensationDays = Integer
        		    		 .parseInt(compensationDaysField.getText().trim());
            
            ContractTypeCrudService service = Factories.service
        		    				      .forContractTypeCrudService();
            service.update(currentContractType);
            
            showSuccess("Contract type updated successfully");
            clearFields();
            
        }  catch (BusinessException ex) {
            showError(ex.getMessage());
        } catch (IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        compensationDaysField.setText("");
        nameField.setEnabled(false);
        compensationDaysField.setEnabled(false);
        updateButton.setEnabled(false);
        currentContractType = null;
    }
}
