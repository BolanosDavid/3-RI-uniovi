package uo.ri.ui.payroll;

import java.awt.*;
import javax.swing.*;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.payroll.view.*;

public class PayrollManagementPanel extends JPanel {
    private static final long serialVersionUID = 3462291258724691197L;
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public PayrollManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        initComponents();
    }
    
    private void initComponents() {
        JLabel title = new JLabel("Payrolls management");
        title.setFont(UIConstants.SECTION_TITLE_FONT);
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setBackground(UIConstants.BACKGROUND_LIGHT);
        contentPanel.add(createWelcomePanel(), "welcome");
        contentPanel.add(new GeneratePayrollsAtDatePanel(), "generateAtDate");
        contentPanel.add(new GeneratePayrollsTodayPanel(), "generateToday");
        contentPanel.add(new DeleteLastMonthPayrollsPanel(), "deleteLastMonth");
        contentPanel.add(new ShowPayrollPanel(), "showPayroll");
        contentPanel.add(new ListPayrollsByMechanicPanel(), "listByMechanic");
        contentPanel.add(new ListPayrollsByProfGroupPanel(), "listByProfGroup");
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
        
        JButton generateDateBtn = ComponentFactory
        					  .createPrimaryButton("Generate at date");
        generateDateBtn.addActionListener(e -> 
        			 contentCardLayout.show(contentPanel, "generateAtDate"));
        
        JButton generateTodayBtn = ComponentFactory
        					     .createPrimaryButton("Generate today");
        generateTodayBtn.addActionListener(e -> 
        			  contentCardLayout.show(contentPanel, "generateToday"));
        
        JButton deleteBtn = ComponentFactory
        					  .createDangerButton("Delete last month");
        deleteBtn.addActionListener(e -> 
        			 contentCardLayout.show(contentPanel, "deleteLastMonth"));
        
        JButton showBtn = ComponentFactory
        					    .createSecondaryButton("Show payroll");
        showBtn.addActionListener(e -> 
        			      contentCardLayout.show(contentPanel, "showPayroll"));
        
        JButton listMechanicBtn = ComponentFactory
        					.createSecondaryButton("List by mechanic");
        listMechanicBtn.addActionListener(e ->
        			  contentCardLayout.show(contentPanel, "listByMechanic"));
        JButton listGroupBtn = ComponentFactory
        				      .createSecondaryButton("List by prof group");
        listGroupBtn.addActionListener(e -> 
        			 contentCardLayout.show(contentPanel, "listByProfGroup"));
        toolbar.add(generateDateBtn);
        toolbar.add(generateTodayBtn);
        toolbar.add(deleteBtn);
        toolbar.add(showBtn);
        toolbar.add(listMechanicBtn);
        toolbar.add(listGroupBtn);
        
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
