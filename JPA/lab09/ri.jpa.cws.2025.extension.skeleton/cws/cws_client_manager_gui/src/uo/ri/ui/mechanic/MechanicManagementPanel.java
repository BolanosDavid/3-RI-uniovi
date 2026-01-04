package uo.ri.ui.mechanic;

import java.awt.*;
import javax.swing.*;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.mechanic.view.*;

public class MechanicManagementPanel extends JPanel {
    private static final long serialVersionUID = 7811260364703171270L;
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public MechanicManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        initComponents();
    }
    
    private void initComponents() {
        JLabel title = new JLabel("Mechanics management");
        title.setFont(UIConstants.SECTION_TITLE_FONT);
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);
        
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setBackground(UIConstants.BACKGROUND_LIGHT);
        
        contentPanel.add(createWelcomePanel(), "welcome");
        contentPanel.add(new AddMechanicPanel(), "add");
        contentPanel.add(new UpdateMechanicPanel(), "update");
        contentPanel.add(new ListCurrentMechanicsPanel(), "listCurrent");
        contentPanel.add(new DeleteMechanicPanel(), "delete");
        contentPanel.add(new ListAllMechanicsPanel(), "listAll");
        
        JPanel toolbar = createToolbar();
        
        JPanel wrapper = new JPanel(new BorderLayout(0, 16));
        wrapper.setBackground(UIConstants.BACKGROUND_LIGHT);
        wrapper.add(toolbar, BorderLayout.NORTH);
        wrapper.add(contentPanel, BorderLayout.CENTER);
        
        add(wrapper, BorderLayout.CENTER);
    }
    
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.setBackground(UIConstants.BACKGROUND_LIGHT);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        
        JButton addBtn = ComponentFactory.createPrimaryButton("Add mechanic");
        addBtn.addActionListener(e ->
        				      contentCardLayout.show(contentPanel, "add"));
        
        JButton updateBtn = ComponentFactory
        					  .createPrimaryButton("Update mechanic");
        updateBtn.addActionListener(e ->
        				   contentCardLayout.show(contentPanel, "update"));
        
        JButton listCurrentBtn = ComponentFactory
        				  .createSecondaryButton("List current mechanics");
        listCurrentBtn.addActionListener(e -> 
        			       contentCardLayout.show(contentPanel, "listCurrent"));
        
        JButton disableBtn = ComponentFactory
        					   .createDangerButton("Delete mechanic");
        disableBtn.addActionListener(e ->
        				   contentCardLayout.show(contentPanel, "delete"));
        
        JButton listAllBtn = ComponentFactory
        					  .createSecondaryButton("List mechanics");
        listAllBtn.addActionListener(e ->
        				   contentCardLayout.show(contentPanel, "listAll"));
        
        toolbar.add(addBtn);
        toolbar.add(updateBtn);
        toolbar.add(listCurrentBtn);
        toolbar.add(disableBtn);
        toolbar.add(listAllBtn);
        
        return toolbar;
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        
        JLabel label = new JLabel("Select an action from the buttons above");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(UIConstants.TEXT_SECONDARY);
        panel.add(label);
        
        return panel;
    }
}
