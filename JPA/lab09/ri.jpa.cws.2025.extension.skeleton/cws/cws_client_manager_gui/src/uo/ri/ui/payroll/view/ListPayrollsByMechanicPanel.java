package uo.ri.ui.payroll.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsByMechanicPanel extends BasePanel {
    private static final long serialVersionUID = 7423565507249622038L;
    private JTextField idField;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("List payrolls by mechanic"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel idLabel = new JLabel("Mechanic ID:");
        idLabel.setFont(UIConstants.LABEL_FONT);
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        idField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(idField, gbc);
        resultArea = ComponentFactory.createStyledTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(16, 0, 8, 0);
        formPanel.add(scrollPane, gbc);
        JButton loadButton = ComponentFactory.createPrimaryButton("Load Payrolls");
        loadButton.addActionListener(e -> handleLoad());
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(loadButton, gbc);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleLoad() {
        try {
            String mechanicId = idField.getText().trim();
            PayrollService service = Factories.service.forPayrollService();
            List<PayrollSummaryDto> payrolls = service.findSummarizedByMechanicId(mechanicId);
            if (payrolls.isEmpty()) {
                resultArea.setText("No payrolls found for mechanic ID: " + mechanicId);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Payrolls for mechanic ID: %s\n", mechanicId));
            sb.append("=".repeat(50)).append("\n\n");
            for (PayrollSummaryDto p : payrolls) {
                sb.append(SwingPrinterAdapter.formatPayrollSummary(p));
            }
            resultArea.setText(sb.toString());
            resultArea.setCaretPosition(0);
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
}
