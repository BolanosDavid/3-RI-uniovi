package uo.ri.ui.contract.view.contracttypes;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.util.exception.BusinessException;

public class DeleteContractTypePanel extends BasePanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Delete contract type"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel idLabel = new JLabel("Contract type name:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        idField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(idField, gbc);
        JButton deleteButton = ComponentFactory
        				      .createDangerButton("Delete Contract Type");
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
            String id = idField.getText().trim();
            
            int confirm = showConfirmation(
                "Are you sure you want to delete this contract type?\n" +
                "This action cannot be undone."
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                ContractTypeCrudService service = Factories.service.forContractTypeCrudService();
                service.delete(id);
                
                showSuccess("Contract type deleted successfully");
                idField.setText("");
            }
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        } catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
}
