package gui;

import businesslogic.BaseProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import dataaccess.ReadFile;
import dataaccess.Serializatior;
import model.Order;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class EmployeeWindow extends JFrame implements Observer{


    private JTable productsTable;
    private MenuItem menuItem = new BaseProduct();
    private JScrollPane scrollPane;
    private JPanel contentPane;
    private JPanel tablePanel;



    private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
    private final Font comboBoxFont = new Font("Cascadia Mono",0, 10);
    private final Color buttonColor = new Color(102,204,204);
    private final Color backgroundColor = new Color(192,217,207);
    private final  Color tableColor = new Color(223,234,226);

    private DeliveryService deliveryService;
    private DefaultTableModel defaultTableModel;



    public EmployeeWindow(String name, DeliveryService deliveryService) {
        super(name);
        this.deliveryService = deliveryService;
        this.prepareGui();
        this.setColorsFonts();
    }

    public void prepareGui(){
        this.setSize(1000, 500);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.contentPane.add(prepareTablePanel(),constraints);

        this.setContentPane(this.contentPane);

    }

    public void printTable(ArrayList<Order> orders){

        String[] columns = {"OrderID","Client","Date","Total Price"};

        this.defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(columns);
        Object[] objects = new Object[7];

        Iterator<Order> iterator = orders.iterator();
        while(iterator.hasNext()){
            Order order = iterator.next();
            objects[0] = order.getOrderID();
            objects[1] = order.getClientName();
            objects[2] = order.getOrderDate();
            objects[3] = order.getPrice();
            defaultTableModel.addRow(objects);
        }

        productsTable.setModel(defaultTableModel);

//        productsTable.getColumnModel().getColumn(0).setPreferredWidth(450);
//        productsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
//        productsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
//        productsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
//        productsTable.getColumnModel().getColumn(4).setPreferredWidth(60);
//        productsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
//        productsTable.getColumnModel().getColumn(6).setPreferredWidth(70);

    }

    private JPanel prepareTablePanel(){

        tablePanel = new JPanel();
        productsTable = new JTable();
        scrollPane = new JScrollPane();

        printTable(deliveryService.getOrders());
        scrollPane.setViewportView(productsTable);
        tablePanel.add(scrollPane);


        return tablePanel;
    }

    private void setColorsFonts(){

        this.contentPane.setBackground(backgroundColor);
        this.tablePanel.setBackground(backgroundColor);
        this.productsTable.setFont(comboBoxFont);
        this.productsTable.getTableHeader().setOpaque(false);
        this.productsTable.getTableHeader().setBackground(buttonColor);
        this.productsTable.getTableHeader().setFont(comboBoxFont);
        this.scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = buttonColor;
            }
        });

        this.productsTable.setBackground(tableColor);
    }


    @Override
    public void update(ArrayList<Order> orders) {
        printTable(orders);
    }
}

