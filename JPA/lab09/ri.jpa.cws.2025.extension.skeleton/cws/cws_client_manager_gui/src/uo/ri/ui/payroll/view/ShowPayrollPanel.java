package uo.ri.ui.payroll.view;

import java.awt.*;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class ShowPayrollPanel extends BasePanel {
    private static final long serialVersionUID = 728135328605438544L;
    private JTextField idField;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Show payroll details"), BorderLayout.NORTH);
        
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        
        JLabel idLabel = new JLabel("Payroll ID:");
        idLabel.setFont(UIConstants.LABEL_FONT);
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        
        idField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(idField, gbc);
        
        resultArea = ComponentFactory.createStyledTextArea();
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(16, 0, 8, 0);
        formPanel.add(scrollPane, gbc);
        
        JButton showButton = ComponentFactory.createPrimaryButton("Show Details");
        showButton.addActionListener(e -> handleShow());
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(showButton, gbc);
        
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleShow() {
	    try {
	        String payrollId = idField.getText();
	        PayrollService service = Factories.service.forPayrollService();
	        PayrollDto dto = service.findById(payrollId)
	            .orElseThrow(() -> new BusinessException("Payroll not found"));
	        
	        String formattedOutput = SwingPrinterAdapter
	        						   .formatPayrollDetails(dto);
	        resultArea.setText(formattedOutput);
	        
	    } catch(BusinessException ex) {
	        showError(ex.getMessage());
	    } catch(IllegalArgumentException i) {
	        showArgumentError(i.getMessage());
	    }
	}

}
