package uo.ri.ui.common;

import java.awt.*;
import javax.swing.*;

public abstract class BasePanel extends JPanel {
 
    private static final long serialVersionUID = 7654318210652808782L;

    protected BasePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        initComponents();
    }
    
    protected abstract void initComponents();
    
    protected JLabel createViewTitle(String titleText) {
        JLabel title = new JLabel(titleText);
        title.setFont(UIConstants.VIEW_TITLE_FONT);
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
        return title;
    }
    
    protected JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    protected GridBagConstraints createGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }
    
    protected void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    protected void showArgumentError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Argument Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    protected int showConfirmation(String message) {
        return JOptionPane.showConfirmDialog(
            this,
            message,
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    }
}
