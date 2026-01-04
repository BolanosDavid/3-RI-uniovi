package uo.ri.ui.payroll.view;

import java.awt.*; 
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

public class GeneratePayrollsAtDatePanel extends BasePanel {
    private static final long serialVersionUID = 8152496879736563998L;
    private JTextField dateField;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Generate payrolls at date"), BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        dateLabel.setFont(UIConstants.LABEL_FONT);
        gbc.gridy = 0;
        formPanel.add(dateLabel, gbc);
        dateField = ComponentFactory.createStyledTextField();
        dateField.setText(LocalDate.now().toString());
        gbc.gridy = 1;
        formPanel.add(dateField, gbc);
        resultArea = ComponentFactory.createStyledTextArea();
        resultArea.setRows(10);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(16, 0, 8, 0);
        formPanel.add(resultScrollPane, gbc);
        JButton generateButton = ComponentFactory
        					  .createSuccessButton("Generate Payrolls");
        generateButton.addActionListener(e -> handleGenerate());
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(generateButton, gbc);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void handleGenerate() {
        try {
            LocalDate date = parseDate(dateField.getText());
            PayrollService service = Factories.service.forPayrollService();
            List<PayrollDto> payrolls = service.generateForPreviousMonthOf(date);
            displayPayrolls(payrolls);
            showSuccess(payrolls.size() + " payroll(s) generated successfully");
            
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. "
            			 + "Please use yyyy-MM-dd (f.ex: 2025-11-14)");
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        } catch (IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
    
    /**
     * Valida y parsea la fecha del campo de texto
     */
    private LocalDate parseDate(String dateText) throws DateTimeParseException {
        if (dateText == null || dateText.trim().isEmpty()) {
            throw new DateTimeParseException("Date cannot be empty", dateText, 0);
        }
        return LocalDate.parse(dateText.trim());
    }
    
    /**
     * Formatea y muestra los payrolls en el área de resultados
     */
    private void displayPayrolls(List<PayrollDto> payrolls) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("✓ %d payroll(s) generated\n", payrolls.size()));
        sb.append("=".repeat(50)).append("\n\n");
        
        if (!payrolls.isEmpty()) {
            sb.append(SwingPrinterAdapter.formatPayrolls(payrolls));
        } else {
            sb.append("No payrolls were generated for the specified date.");
        }
        
        resultArea.setText(sb.toString());
        resultArea.setCaretPosition(0);
    }
}
