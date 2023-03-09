package gui;

import businesslogic.DeliveryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Report2Window extends JFrame {

    private JPanel contentPane;
    private JPanel operationsPanel;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JLabel numberLabel;
    private JTextField numberTextField;
    private JButton generateButton;

    private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
    private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 15);
    private final Color buttonColor = new Color(102,204,204);
    private final Color backgroundColor = new Color(192,217,207);
    private final  Color tableColor = new Color(223,234,226);

    private final DeliveryService deliveryService;


    public Report2Window(String name, DeliveryService deliveryService) {
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
        this.titleLabel = new JLabel("Products ordered more than", JLabel.CENTER);
        this.titleLabel.setFont(titleFont);
        this.titlePanel.add(titleLabel);
        this.titleLabel = new JLabel( "a specified number of times so far",SwingConstants.CENTER);
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
        this.numberLabel = new JLabel("Specify a number: ",SwingConstants.RIGHT);
        this.operationsPanel.add(numberLabel,constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.numberTextField = new JTextField();
        this.numberTextField.setColumns(2);
        this.operationsPanel.add(numberTextField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        JPanel auxPanel = new JPanel();
        auxPanel.setBackground(backgroundColor);
        this.operationsPanel.add(auxPanel,constraints);


        this.contentPane.add(this.operationsPanel);

    }

    private void setColorsFonts(){

        this.titlePanel.setBackground(backgroundColor);
        this.operationsPanel.setBackground(backgroundColor);
        this.titleLabel.setFont(titleFont);
        this.numberLabel.setFont(font);
        this.contentPane.setBackground(backgroundColor);
        this.generateButton.setFont(font);
        this.generateButton.setBackground(buttonColor);
        this.generateButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));
        this.numberTextField.setFont(font);
        this.numberTextField.setBackground(tableColor);
    }

    public JTextField getNumberTextField() {
        return numberTextField;
    }

    public void createActionListener(){

        generateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int timesOrdered = Integer.parseInt(getNumberTextField().getText());
                deliveryService.report2(timesOrdered);

            }
        });
    }

}
