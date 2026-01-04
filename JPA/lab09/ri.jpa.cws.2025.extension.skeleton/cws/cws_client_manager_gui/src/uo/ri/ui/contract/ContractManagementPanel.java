package uo.ri.ui.contract;

import java.awt.*;
import javax.swing.*;
import uo.ri.ui.common.UIConstants;

public class ContractManagementPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public ContractManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        initComponents();
    }
    
    private void initComponents() {
        JLabel title = new JLabel("Contracts management");
        title.setFont(UIConstants.SECTION_TITLE_FONT);
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.addTab("Contracts", new ContractsSubPanel());
        tabbedPane.addTab("Contract Types", new ContractTypesSubPanel());
        tabbedPane.addTab("Professional Groups", new ProfessionalGroupsSubPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }
}
