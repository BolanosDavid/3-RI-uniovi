package uo.ri.ui.contract.view.contracttypes;

import java.awt.*;
import java.util.Optional;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class FindContractTypePanel extends BasePanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField nameField;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("Find contract type by name"), BorderLayout.NORTH);
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = createGBC();
        JLabel nameLabel = new JLabel("Contract type name:");
        nameLabel.setFont(UIConstants.LABEL_FONT);
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        nameField = ComponentFactory.createStyledTextField();
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);
        resultArea = ComponentFactory.createStyledTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(16, 0, 8, 0);
        formPanel.add(scrollPane, gbc);
        JButton findButton = ComponentFactory.createPrimaryButton("Find");
        findButton.addActionListener(e -> handleFind());
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(findButton, gbc);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void handleFind() {
        try {
            String name = nameField.getText().trim();
            ContractTypeCrudService service = Factories.service
        		     				      .forContractTypeCrudService();
            Optional<ContractTypeDto> result = service.findByName(name);
            
            if (!result.isPresent()) {
                resultArea.setText("Contract type not found with name: " 
                								      + name);
                return;
            }
            
            String formattedOutput = SwingPrinterAdapter.formatContractType(result.get());
            resultArea.setText(formattedOutput);
            resultArea.setCaretPosition(0);
            
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        } catch(IllegalArgumentException i) {
            showArgumentError(i.getMessage());
        }
    }
}
