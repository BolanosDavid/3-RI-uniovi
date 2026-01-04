package uo.ri.ui;

import java.awt.*; 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uo.ri.ui.common.UIConstants;
import uo.ri.ui.mechanic.MechanicManagementPanel;
import uo.ri.ui.contract.ContractManagementPanel;
import uo.ri.ui.payroll.PayrollManagementPanel;

public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    
    public MainWindow() {
        super("CWS – Manager console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        initUI();
    }
    
    private void initUI() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.BACKGROUND_LIGHT);
        
        JPanel header = createHeader();
        getContentPane().add(header, BorderLayout.NORTH);
        
        JList<String> menuList = createMenu();
        JPanel sidebarWrapper = new JPanel(new BorderLayout());
        sidebarWrapper.setBackground(UIConstants.SIDEBAR_BG);
        sidebarWrapper.add(menuList, BorderLayout.CENTER);
        sidebarWrapper.setBorder(BorderFactory.createMatteBorder(
                                           0, 0, 0, 1, UIConstants.BORDER_COLOR
        ));
        getContentPane().add(sidebarWrapper, BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(UIConstants.BACKGROUND_LIGHT);
        
        cardsPanel.add(new MechanicManagementPanel(), 
                       				    UIConstants.MECHANIC_VIEW);
        cardsPanel.add(new ContractManagementPanel(),
                                                   UIConstants.CONTRACTS_VIEW);
        cardsPanel.add(new PayrollManagementPanel(), 
                                                      UIConstants.PAYROLLS_VIEW);
        cardsPanel.add(createPlaceholderPanel("Parts management", 
                                        "Spare parts module - To be implemented"),
                                                   UIConstants.SPAREPART_VIEW);
        cardsPanel.add(createPlaceholderPanel("Vehicle types management", 
                                       "Vehicle types module - To be implemented"), 
                                                 UIConstants.VEHICLETYPE_VIEW);
        
        getContentPane().add(cardsPanel, BorderLayout.CENTER);
        
        menuList.setSelectedIndex(0);
        cardLayout.show(cardsPanel, UIConstants.MECHANIC_VIEW);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, UIConstants.BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));
        
        JLabel appTitle = new JLabel("CWS – Manager console");
        appTitle.setFont(UIConstants.TITLE_FONT);
        appTitle.setForeground(UIConstants.TEXT_PRIMARY);
        appTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel appSubtitle = new JLabel("Workshop administration area");
        appSubtitle.setFont(UIConstants.SUBTITLE_FONT);
        appSubtitle.setForeground(UIConstants.TEXT_SECONDARY);
        appSubtitle.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        appSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        header.add(appTitle);
        header.add(appSubtitle);
        
        return header;
    }
    
    private JList<String> createMenu() {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement(UIConstants.MECHANIC_VIEW);
        model.addElement(UIConstants.CONTRACTS_VIEW);
        model.addElement(UIConstants.PAYROLLS_VIEW);
        model.addElement(UIConstants.SPAREPART_VIEW);
        model.addElement(UIConstants.VEHICLETYPE_VIEW);
        
        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(52);
        list.setFont(UIConstants.LABEL_FONT);
        list.setBackground(UIConstants.SIDEBAR_BG);
        list.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        list.setPreferredSize(new Dimension(280, 0));
        
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                
                JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );
                
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4, 8, 4, 8),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                
                if (isSelected) {
                    label.setBackground(UIConstants.SIDEBAR_SELECTED);
                    label.setForeground(UIConstants.PRIMARY_COLOR);
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                    label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(4, 8, 4, 8),
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 3, 0, 0, 
                                                      UIConstants.PRIMARY_COLOR),
                            BorderFactory.createEmptyBorder(8, 12, 8, 12)
                        )
                    ));
                } else {
                    label.setBackground(UIConstants.SIDEBAR_BG);
                    label.setForeground(UIConstants.TEXT_PRIMARY);
                }
                
                label.setOpaque(true);
                return label;
            }
        });
        
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selected = list.getSelectedValue();
                    if (selected != null) {
                        cardLayout.show(cardsPanel, selected);
                    }
                }
            }
        });
        
        return list;
    }
    
    private JPanel createPlaceholderPanel(String titleText, String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.BACKGROUND_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        JLabel title = new JLabel(titleText);
        title.setFont(UIConstants.SECTION_TITLE_FONT);
        title.setForeground(UIConstants.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createLineBorder(
                                                   UIConstants.BORDER_COLOR, 1));
        
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(UIConstants.TEXT_SECONDARY);
        welcomePanel.add(label);
        
        panel.add(welcomePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                                     UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
