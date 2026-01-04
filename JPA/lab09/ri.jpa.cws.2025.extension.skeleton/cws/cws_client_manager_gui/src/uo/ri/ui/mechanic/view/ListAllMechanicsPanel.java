package uo.ri.ui.mechanic.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.common.BasePanel;
import uo.ri.ui.common.ComponentFactory;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.util.SwingPrinterAdapter;
import uo.ri.util.exception.BusinessException;

public class ListAllMechanicsPanel extends BasePanel {
    private static final long serialVersionUID = -5832787523870087684L;
    private JTextArea resultArea;
    
    @Override
    protected void initComponents() {
        add(createViewTitle("List all mechanics"), BorderLayout.NORTH);
        resultArea = ComponentFactory.createStyledTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder
                             			 (UIConstants.BORDER_COLOR, 1));
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
	        MechanicCrudService service = Factories
	        					 .service.forMechanicCrudService();
	        List<MechanicDto> mechanics = service.findAll();
	        
	        String formattedOutput = SwingPrinterAdapter
	        						.formatMechanics(mechanics);
	        resultArea.setText(formattedOutput);
	        
	    } catch (BusinessException ex) {
	        showError(ex.getMessage());
	    }
	}

}
