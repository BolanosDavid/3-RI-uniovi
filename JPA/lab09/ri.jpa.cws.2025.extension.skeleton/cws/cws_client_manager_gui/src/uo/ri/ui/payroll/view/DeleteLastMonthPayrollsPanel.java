package uo.ri.ui.payroll.view;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollsPanel extends BasePanel {
    private static final long serialVersionUID = 4808652208466061855L;

    @Override
    protected void initComponents() {
        add(createViewTitle("Delete last month payrolls"), BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel desc = new JLabel(
            "<html>This action will delete all payrolls from the last "
            + "generated month.<br> This operation cannot be undone.</html>"
        );
        desc.setFont(UIConstants.LABEL_FONT);
        desc.setForeground(UIConstants.TEXT_SECONDARY);
        contentPanel.add(desc, gbc);
        JButton deleteButton = ComponentFactory
        				.createDangerButton("Delete Last Month Payrolls");
        deleteButton.addActionListener(e -> handleDelete());
        gbc.gridy = 1;
        contentPanel.add(deleteButton, gbc);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleDelete() {
        int confirm = showConfirmation(
            "Are you sure you want to delete all last month payrolls?\n" +
            "This action cannot be undone."
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PayrollService service = Factories.service.forPayrollService();
                service.deleteLastGenerated();
                
                showSuccess("Last month's payrolls deleted successfully");
                
            } catch (BusinessException ex) {
                showError(ex.getMessage());
            }
        }
    }
}
