package uo.ri.ui.mechanic.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class ListCurrentMechanicsPanel extends BasePanel {
    private static final long serialVersionUID = 857982178260725123L;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("List current mechanics"), BorderLayout.NORTH);
        resultArea = ComponentFactory.createStyledTextArea();
        resultArea.setText("Click 'Load Data' to view mechanics "
        		+ "					      with active contracts");
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        add(scrollPane, BorderLayout.CENTER);
        
        JButton loadButton = ComponentFactory.createPrimaryButton("Load Data");
        loadButton.addActionListener(e -> loadData());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        bottomPanel.add(loadButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
	    try {
	        ContractCrudService service = Factories.service
	        						.forContractCrudService();
	        List<ContractDto> contracts = service.findInforceContracts();
	        
	        if (contracts.isEmpty()) {
	            resultArea.setText("No mechanics with active "
	            		+ "contracts found.");
	            return;
	        }
	     
	        String formattedOutput = SwingPrinterAdapter
	        						.formatContracts(contracts);
	        resultArea.setText(formattedOutput);
	        
	    } catch (BusinessException ex) {
	        showError(ex.getMessage());
	    }
	}

}
