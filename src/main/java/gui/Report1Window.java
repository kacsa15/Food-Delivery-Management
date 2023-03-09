package gui;

import businesslogic.DeliveryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Report1Window extends JFrame {

    private JPanel contentPane;
    private JPanel operationsPanel;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JLabel startHourLabel;
    private JLabel endHourLabel;
    private JTextField startHourTextField;
    private JTextField endHoureTextField;
    private JButton generateButton;

    private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
    private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 15);
    private final Color buttonColor = new Color(102,204,204);
    private final Color backgroundColor = new Color(192,217,207);
    private final  Color tableColor = new Color(223,234,226);

    private final DeliveryService deliveryService;


    public Report1Window(String name, DeliveryService deliveryService) {
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
        this.titlePanel.setLayout(new GridLayout(1,0));
        this.titleLabel = new JLabel("Time interval of the orders report", JLabel.CENTER);
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
        this.startHourLabel = new JLabel("Start hour: ",SwingConstants.RIGHT);
        this.operationsPanel.add(startHourLabel,constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.startHourTextField = new JTextField();
        this.startHourTextField.setColumns(2);
        this.operationsPanel.add(startHourTextField,constraints);

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
        this.endHourLabel = new JLabel("End hour: ",SwingConstants.RIGHT);
        this.operationsPanel.add(endHourLabel,constraints);


        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.endHoureTextField = new JTextField();
        this.endHoureTextField.setColumns(2);
        this.operationsPanel.add(endHoureTextField,constraints);

        this.contentPane.add(this.operationsPanel);

    }

    private void setColorsFonts(){

        this.titlePanel.setBackground(backgroundColor);
        this.operationsPanel.setBackground(backgroundColor);
        this.titleLabel.setFont(titleFont);
        this.startHourLabel.setFont(font);
        this.endHourLabel.setFont(font);
        this.contentPane.setBackground(backgroundColor);
        this.generateButton.setFont(font);
        this.generateButton.setBackground(buttonColor);
        this.generateButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));
        this.endHoureTextField.setBackground(tableColor);
        this.endHoureTextField.setFont(font);
        this.startHourTextField.setFont(font);
        this.startHourTextField.setBackground(tableColor);
    }

    public JTextField getStartHourTextField() {
        return startHourTextField;
    }

    public JTextField getEndHoureTextField() {
        return endHoureTextField;
    }

    public void createActionListener(){

        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int startHour = Integer.parseInt(getStartHourTextField().getText());
                int endHour = Integer.parseInt(getEndHoureTextField().getText());

                deliveryService.report1(startHour,endHour);

            }
        });
    }

}
