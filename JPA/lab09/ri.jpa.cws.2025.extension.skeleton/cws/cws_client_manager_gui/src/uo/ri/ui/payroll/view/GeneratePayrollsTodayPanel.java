package uo.ri.ui.payroll.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrollsTodayPanel extends BasePanel {
 
    private static final long serialVersionUID = -3379225276237118964L;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Generate payrolls today"), BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        JLabel description = new JLabel("Click the button to generate payrolls"
        								 + " for last month");
        description.setFont(UIConstants.LABEL_FONT);
        description.setForeground(UIConstants.TEXT_SECONDARY);
        description.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        contentPanel.add(description, BorderLayout.NORTH);
        
        resultArea = ComponentFactory.createStyledTextArea();
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory
                              .createLineBorder(UIConstants.BORDER_COLOR, 1));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        JButton generateButton = ComponentFactory
        					     .createSuccessButton("Generate Now");
        generateButton.addActionListener(e -> handleGenerate());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        bottomPanel.add(generateButton);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleGenerate() {
        try {
            PayrollService service = Factories.service.forPayrollService();
            List<PayrollDto> payrolls = service.generateForPreviousMonth();
            displayPayrolls(payrolls);
            showSuccess(payrolls.size() + " payroll(s) generated successfully");
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        } 
    }
    
    /**
     * Formatea y muestra los payrolls generados
     */
    private void displayPayrolls(List<PayrollDto> payrolls) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("✓ %d payroll(s) generated\n", payrolls.size()));
        sb.append("=".repeat(50)).append("\n\n");
        if (!payrolls.isEmpty()) {
            sb.append(SwingPrinterAdapter.formatPayrolls(payrolls));
        } else {
            sb.append("No payrolls were generated for the previous month.");
        }
        resultArea.setText(sb.toString());
        resultArea.setCaretPosition(0); 
    }
}
