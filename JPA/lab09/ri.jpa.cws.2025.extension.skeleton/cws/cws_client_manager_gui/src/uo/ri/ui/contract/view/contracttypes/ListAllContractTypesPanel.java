package uo.ri.ui.contract.view.contracttypes;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class ListAllContractTypesPanel extends BasePanel {
    private static final long serialVersionUID = 1L;
    
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("List all contract types"), BorderLayout.NORTH);
        resultArea = ComponentFactory.createStyledTextArea();
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
            ContractTypeCrudService service = Factories.service.forContractTypeCrudService();
            List<ContractTypeDto> contractTypes = service.findAll();
            
            if (contractTypes.isEmpty()) {
                resultArea.setText("No contract types found.");
                return;
            }
            String formattedOutput = SwingPrinterAdapter.formatContractTypes(contractTypes);
            resultArea.setText(formattedOutput);
            resultArea.setCaretPosition(0);
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }
    }
}
