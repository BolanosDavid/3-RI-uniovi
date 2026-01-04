package uo.ri.ui.mechanic.view;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanicPanel extends BasePanel {
    private static final long serialVersionUID = -3437176529398106218L;
    private JTextField idField;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Delete mechanic"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel idLabel = new JLabel("Mechanic ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        idField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(idField, gbc);
        JButton deleteButton = ComponentFactory.createDangerButton("Delete Mechanic");
        deleteButton.addActionListener(e -> handleDelete());
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(deleteButton, gbc);
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(Box.createVerticalGlue(), gbc);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleDelete() {
        try {
            String id = idField.getText();
            
            int confirm = showConfirmation(
                "Are you sure you want to delete this mechanic?"
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                MechanicCrudService service = Factories
                		                          .service.forMechanicCrudService();
                service.delete(id);
                showSuccess("The mechanic has been removed");
                idField.setText("");
            }
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
}
