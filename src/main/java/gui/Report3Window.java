package gui;

import businesslogic.DeliveryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Report3Window extends JFrame {

    private JPanel contentPane;
    private JPanel operationsPanel;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JLabel minOrderLabel;
    private JLabel minAmountLabel;
    private JTextField minOrderTextField;
    private JTextField minAmountTextField;
    private JButton generateButton;

    private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
    private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 15);
    private final Color buttonColor = new Color(102,204,204);
    private final Color backgroundColor = new Color(192,217,207);
    private final  Color tableColor = new Color(223,234,226);

    private final DeliveryService deliveryService;


    public Report3Window(String name, DeliveryService deliveryService) {
        super(name);
        this.deliveryService = deliveryService;
        this.prepareGui();
        this.setColorsFonts();
    }

    public void prepareGui(){
        this.setSize(350, 250);
        this.contentPane = new JPanel(new GridLayout(3, 1));
        this.prepareTitlePanel();
        this.prepareReportPanel();
        this.prepareButtonPanel();
        this.setContentPane(this.contentPane);
        createActionListener();
    }

    private void prepareTitlePanel(){
        this.titlePanel = new JPanel();
        this.titlePanel.setLayout(new GridLayout(2,0));
        this.titleLabel = new JLabel("List clients based on a specified ", JLabel.CENTER);
        this.titleLabel.setFont(titleFont);
        this.titlePanel.add(titleLabel);
        this.titleLabel = new JLabel( "criteria",SwingConstants.CENTER);
        this.titlePanel.add(titleLabel);
        this.contentPane.add(this.titlePanel);

    }

    private void prepareButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        this.generateButton = new JButton("Generate report");
        this.generateButton.setActionCommand("INTEGRATION");
        //this.generateButton.addActionListener(this.controller)
        buttonPanel.add(this.generateButton);
        this.contentPane.add(buttonPanel);
    }

    private void prepareReportPanel(){
        this.operationsPanel = new JPanel();
        this.operationsPanel.setLayout(new GridBagLayout());


        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.minOrderLabel = new JLabel("Min. number of times for an order: ",SwingConstants.RIGHT);
        this.operationsPanel.add(minOrderLabel,constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.minOrderTextField = new JTextField();
        this.minOrderTextField.setColumns(2);
        this.operationsPanel.add(minOrderTextField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        JPanel auxPanel = new JPanel();
        auxPanel.setBackground(backgroundColor);
        this.operationsPanel.add(auxPanel,constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.minAmountLabel = new JLabel("Min. amount per order: ",SwingConstants.RIGHT);
        this.operationsPanel.add(minAmountLabel,constraints);


        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.minAmountTextField = new JTextField();
        this.minAmountTextField.setColumns(2);
        this.operationsPanel.add(minAmountTextField,constraints);

        this.contentPane.add(this.operationsPanel);

    }

    private void setColorsFonts(){

        this.titlePanel.setBackground(backgroundColor);
        this.operationsPanel.setBackground(backgroundColor);
        this.titleLabel.setFont(titleFont);
        this.minOrderLabel.setFont(font);
        this.minAmountLabel.setFont(font);
        this.contentPane.setBackground(backgroundColor);
        this.generateButton.setFont(font);
        this.generateButton.setBackground(buttonColor);
        this.generateButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));
        this.minOrderTextField.setBackground(tableColor);
        this.minAmountTextField.setFont(font);
        this.minOrderTextField.setFont(font);
        this.minAmountTextField.setBackground(tableColor);
    }

    public JTextField getMinOrderTextField() {
        return minOrderTextField;
    }

    public JTextField getMinAmountTextField() {
        return minAmountTextField;
    }

    public void createActionListener(){

        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int minTimes = Integer.parseInt(getMinOrderTextField().getText());
                int minAmount = Integer.parseInt(getMinAmountTextField().getText());
                deliveryService.report3(minTimes,minAmount);

            }
        });
    }


}
