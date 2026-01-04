package uo.ri.ui.contract;

import java.awt.*;
import javax.swing.*;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.contract.view.contracttypes.AddContractTypePanel;
import uo.ri.ui.contract.view.contracttypes.DeleteContractTypePanel;
import uo.ri.ui.contract.view.contracttypes.FindContractTypePanel;
import uo.ri.ui.contract.view.contracttypes.ListAllContractTypesPanel;
import uo.ri.ui.contract.view.contracttypes.UpdateContractTypePanel;

public class ContractTypesSubPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public ContractTypesSubPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_LIGHT);
        initComponents();
    }
    
    private void initComponents() {
        JLabel title = new JLabel("Contract types management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        add(title, BorderLayout.NORTH);
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setBackground(UIConstants.BACKGROUND_LIGHT);
        contentPanel.add(createWelcomePanel(), "welcome");
        contentPanel.add(new AddContractTypePanel(), "add");
        contentPanel.add(new UpdateContractTypePanel(), "update");
        contentPanel.add(new DeleteContractTypePanel(), "delete");
        contentPanel.add(new FindContractTypePanel(), "find");
        contentPanel.add(new ListAllContractTypesPanel(), "listAll");
        JPanel toolbar = createToolbar();
        JPanel wrapper = new JPanel(new BorderLayout(0, 12));
        wrapper.setBackground(UIConstants.BACKGROUND_LIGHT);
        wrapper.add(toolbar, BorderLayout.NORTH);
        wrapper.add(contentPanel, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }
    
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setBackground(UIConstants.BACKGROUND_LIGHT);
        JButton addBtn = ComponentFactory.createPrimaryButton("Add");
        addBtn.addActionListener(e -> 
        				      contentCardLayout.show(contentPanel, "add"));
        
        JButton updateBtn = ComponentFactory.createPrimaryButton("Update");
        updateBtn.addActionListener(e ->
                                   contentCardLayout.show(contentPanel, "update"));
        
        JButton deleteBtn = ComponentFactory.createDangerButton("Delete");
        deleteBtn.addActionListener(e ->
                                    contentCardLayout.show(contentPanel, "delete"));
        
        JButton findBtn = ComponentFactory
        		                            .createSecondaryButton("Find by name");
        findBtn.addActionListener(e ->
                                      contentCardLayout.show(contentPanel, "find"));
        
        JButton listBtn = ComponentFactory.createSecondaryButton("List all");
        listBtn.addActionListener(e ->
                                    contentCardLayout.show(contentPanel, "listAll"));
        toolbar.add(addBtn);
        toolbar.add(updateBtn);
        toolbar.add(deleteBtn);
        toolbar.add(findBtn);
        toolbar.add(listBtn);
        return toolbar;
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        JLabel label = new JLabel("Select an action from the buttons above");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(UIConstants.TEXT_SECONDARY);
        panel.add(label);
        
        return panel;
    }
}
