package uo.ri.ui.contract.view.contracttypes;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class AddContractTypePanel extends BasePanel {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JTextField compensationDaysField;

    @Override
    protected void initComponents() {
	add(createViewTitle("Add new contract type"),
	    BorderLayout.NORTH);
	JPanel formPanel = createFormPanel();
	GridBagConstraints gbc = createGBC();
	JLabel nameLabel = new JLabel("Contract type name:");
	nameLabel.setFont(new Font("Segoe UI",Font.PLAIN,14));
	gbc.gridy = 0;
	formPanel.add(nameLabel,gbc);
	nameField = ComponentFactory.createStyledTextField();
	gbc.gridy = 1;
	formPanel.add(nameField,gbc);
	JLabel compensationLabel = new JLabel("Compensation days:");
	compensationLabel.setFont(new Font("Segoe UI",Font.PLAIN,14));
	gbc.gridy = 2;
	formPanel.add(compensationLabel,gbc);
	compensationDaysField = ComponentFactory.createStyledTextField();
	gbc.gridy = 3;
	formPanel.add(compensationDaysField,gbc);
	JButton addButton = ComponentFactory
					.createSuccessButton("Add Contract Type");
	addButton.addActionListener(e -> handleAdd());
	gbc.gridy = 4;
	gbc.insets = new Insets(20,0,0,0);
	gbc.anchor = GridBagConstraints.EAST;
	gbc.fill = GridBagConstraints.NONE;
	formPanel.add(addButton,gbc);
	gbc.gridy = 5;
	gbc.weighty = 1.0;
	gbc.fill = GridBagConstraints.BOTH;
	formPanel.add(Box.createVerticalGlue(),gbc);
	add(formPanel,BorderLayout.CENTER);
    }

    private void
	    handleAdd() {
	try {
	    ContractTypeDto ct = new ContractTypeDto();
	    ct.name = nameField.getText()
			       .trim();
	    ct.compensationDays = Integer.parseInt(compensationDaysField.getText()
									.trim());
	    ContractTypeCrudService service = Factories.service
			    			     .forContractTypeCrudService();
	    ct = service.create(ct);
	    showSuccess("Contract type created successfully with ID: " + ct.id);
	    clearFields();
	}  catch (BusinessException ex) {
	    showError(ex.getMessage());
	}catch(IllegalArgumentException i) {
	    showArgumentError(i.getMessage());
	}
    }

    private void
	    clearFields() {
	nameField.setText("");
	compensationDaysField.setText("");
    }
}
