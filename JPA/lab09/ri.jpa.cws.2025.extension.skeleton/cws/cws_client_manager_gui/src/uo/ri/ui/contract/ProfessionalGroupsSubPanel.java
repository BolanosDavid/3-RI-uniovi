package uo.ri.ui.contract;

import java.awt.*;
import javax.swing.*;
import uo.ri.ui.common.UIConstants;

public class ProfessionalGroupsSubPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private CardLayout contentCardLayout;
    private JPanel contentPanel;
    
    public ProfessionalGroupsSubPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND_LIGHT);
        initComponents();
    }
    
    private void initComponents() {
        JLabel title = new JLabel("Professional groups management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        add(title, BorderLayout.NORTH);
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        contentPanel.setBackground(UIConstants.BACKGROUND_LIGHT);
        contentPanel.add(createWelcomePanel(), "welcome");
        JPanel wrapper = new JPanel(new BorderLayout(0, 12));
        wrapper.setBackground(UIConstants.BACKGROUND_LIGHT);
        wrapper.add(contentPanel, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        JLabel label = new JLabel("ProfessionalGroup module- To be implemented");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(UIConstants.TEXT_SECONDARY);
        panel.add(label);
        
        return panel;
    }
}
