package uo.ri.ui.mechanic.view;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class AddMechanicPanel extends BasePanel {
    private static final long serialVersionUID = -70293813661411333L;
    private JTextField doiField;
    private JTextField nameField;
    private JTextField surnameField;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Add new mechanic"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel doiLabel = new JLabel("DOI/NIF:");
        doiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(doiLabel, gbc);
        doiField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(doiField, gbc);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 2;
        formPanel.add(nameLabel, gbc);
        nameField = ComponentFactory.createStyledTextField();
        gbc.gridy = 3;
        formPanel.add(nameField, gbc);
        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 4;
        formPanel.add(surnameLabel, gbc);
        surnameField = ComponentFactory.createStyledTextField();
        gbc.gridy = 5;
        formPanel.add(surnameField, gbc);
        JButton saveButton = ComponentFactory.createSuccessButton("Add Mechanic");
        saveButton.addActionListener(e -> handleSave());
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(saveButton, gbc);
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(Box.createVerticalGlue(), gbc);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleSave() {
        try {
            MechanicDto m = new MechanicDto();
            m.nif = doiField.getText();
            m.name = nameField.getText();
            m.surname = surnameField.getText();
            
            MechanicCrudService service = Factories
        		    				  .service.forMechanicCrudService();
            m = service.create(m);
            
            showSuccess("New mechanic added with ID: " + m.id);
            clearFields();
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    private void clearFields() {
        doiField.setText("");
        nameField.setText("");
        surnameField.setText("");
    }
}
