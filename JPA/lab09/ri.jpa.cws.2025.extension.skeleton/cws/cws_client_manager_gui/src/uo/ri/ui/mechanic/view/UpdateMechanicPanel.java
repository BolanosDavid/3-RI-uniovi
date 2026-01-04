package uo.ri.ui.mechanic.view;

import java.awt.*;
import java.util.Optional;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanicPanel extends BasePanel {
    private static final long serialVersionUID = 7421822477999644173L;
    private JTextField idField;
    private JTextField nameField;
    private JTextField surnameField;
    private JButton updateButton;
    private MechanicDto currentMechanic;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Update mechanic"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel idLabel = new JLabel("Mechanic ID:");
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
        JLabel nameLabel = new JLabel("Name:");
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
        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 5;
        formPanel.add(surnameLabel, gbc);
        surnameField = ComponentFactory.createStyledTextField();
        surnameField.setEnabled(false);
        gbc.gridy = 6;
        formPanel.add(surnameField, gbc);
        updateButton = ComponentFactory.createSuccessButton("Update Mechanic");
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
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void handleSearch() {
        try {
            String id = idField.getText();
            MechanicCrudService service = Factories.service.forMechanicCrudService();
            Optional<MechanicDto> res = service.findById(id);
            
            if (!res.isPresent()) {
                throw new BusinessException("There is no mechanic with that id");
            }
            
            currentMechanic = res.get();
            nameField.setText(currentMechanic.name);
            surnameField.setText(currentMechanic.surname);
            nameField.setEnabled(true);
            surnameField.setEnabled(true);
            updateButton.setEnabled(true);
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    private void handleUpdate() {
        try {
            if (currentMechanic == null) {
                throw new BusinessException("Please search for a mechanic first");
            }
            currentMechanic.name = nameField.getText();
            currentMechanic.surname = surnameField.getText();
            MechanicCrudService service = Factories.service.forMechanicCrudService();
            service.update(currentMechanic);
            showSuccess("The mechanic has been updated");
            clearFields();
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        surnameField.setText("");
        nameField.setEnabled(false);
        surnameField.setEnabled(false);
        updateButton.setEnabled(false);
        currentMechanic = null;
    }
}
