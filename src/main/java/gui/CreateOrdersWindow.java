package gui;

import businesslogic.BaseProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import dataaccess.Serializatior;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Iterator;


public class CreateOrdersWindow extends JFrame {


        private JTable productsTable;
        private MenuItem menuItem = new BaseProduct();
        private JScrollPane scrollPane;
        private JPanel contentPane;
        private JPanel tablePanel;
        private JPanel operationsPanel;
        private JLabel titleLabel;
        private JLabel totalPriceLabel;
        private JLabel priceLabel;
        private JPanel titlePanel;
        public ArrayList<MenuItem> menuItems = new ArrayList<>();
        private JButton reloadTable;
        private JButton placeOrder;

        private ClientsWindow clientsWindow;


        private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
        private final Font comboBoxFont = new Font("Cascadia Mono",0, 10);
        private final Font resultFont = new Font("Cascadia Mono", Font.ITALIC, 22);
        private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 22);
        private final Color buttonColor = new Color(102,204,204);
        private final Color backgroundColor = new Color(192,217,207);
        private final  Color tableColor = new Color(223,234,226);

        private final DefaultTableModel defaultTableModel;
        private DeliveryService deliveryService;
        private String username;


        public CreateOrdersWindow(String name, ClientsWindow clientsWindow, DeliveryService deliveryService,String username) {
            super(name);
            this.menuItems = new ArrayList<>();
            this.defaultTableModel = new DefaultTableModel();
            this.deliveryService = deliveryService;
            this.username = username;
            this.prepareGui();
            this.setColorsFonts();
            this.clientsWindow = clientsWindow;
        }


        public void prepareGui(){
            this.setSize(500, 350);
            this.contentPane = new JPanel(new GridLayout(3,1));
            this.prepareTitlePage();
            this.prepareTablePanel();
            this.prepareOperationsPanel();
            this.setContentPane(this.contentPane);
          //  menuItems = new ArrayList<>();
            createActionListeners();
        }

        private void prepareTitlePage(){

            this.titlePanel= new JPanel();

            this.titleLabel = new JLabel("Place an order");
            this.titlePanel.add(titleLabel);
            this.contentPane.add(titlePanel);

        }

        public void printTable(ArrayList<MenuItem> menuItems) {

            tablePanel = new JPanel();

            String[] columns = {"Name","Price","Quantity"};

            defaultTableModel.setColumnIdentifiers(columns);

            Object[] objects = new Object[3];
            Iterator<MenuItem> iterator = menuItems.iterator();
            while(iterator.hasNext()){
                MenuItem currentItem = iterator.next();
                objects[0] = currentItem.getNameProduct();
                objects[1] = currentItem.computePrice();
                objects[2] = 0;

                defaultTableModel.addRow(objects);
            }

            productsTable = new JTable(defaultTableModel);
            scrollPane = new JScrollPane();
            scrollPane.setBounds(250,100,100,300);
            productsTable.getColumnModel().getColumn(0).setPreferredWidth(450);
            productsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            productsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            scrollPane.setViewportView(productsTable);
            this.tablePanel.add( this.scrollPane);
        }

        private void prepareTablePanel(){

           // ArrayList<MenuItem> menuItems = new ArrayList<>();

            printTable(menuItems);

            this.contentPane.add(tablePanel);
        }


        private void prepareOperationsPanel(){

            this.operationsPanel = new JPanel();
            this.operationsPanel.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.insets = new Insets(5,5,5,5);

            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;

            this.totalPriceLabel = new JLabel("Your order's value is: ", SwingConstants.RIGHT);
            //this.addProductButton.addActionListener(this.controller);
            this.operationsPanel.add(this.totalPriceLabel,constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;

            this.priceLabel = new JLabel("",SwingConstants.LEFT);
            //this.addProductButton.addActionListener(this.controller);
            this.operationsPanel.add(this.priceLabel,constraints);

            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;

            this.reloadTable = new JButton("Reload table");
           // this.showTable.setActionCommand("ADDITION");
            //this.addProductButton.addActionListener(this.controller);
            this.operationsPanel.add(this.reloadTable,constraints);

            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;

            this.placeOrder = new JButton("Place order");
            // this.showTable.setActionCommand("ADDITION");
            //this.addProductButton.addActionListener(this.controller);
            this.operationsPanel.add(this.placeOrder,constraints);


            this.contentPane.add(operationsPanel);
        }

        private void createActionListeners(){

            reloadTable.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int quantity = 1;
                   // menuItems.add(menuItem);
                    menuItem = clientsWindow.getMenuItem();

                    MenuItem menuItem2 = new BaseProduct(menuItem.getNameProduct(),menuItem.computePrice(),menuItem.computeRating()
                    ,menuItem.computeFats(),menuItem.computeProteins(),menuItem.computeSodium(),menuItem.computeCalories());

                    defaultTableModel.addRow(new Object[]{
                            menuItem.getNameProduct(),
                            menuItem.computePrice(),
                            quantity
                    });
                    System.out.println();
                    for(MenuItem menuItem: menuItems){
                        System.out.println(menuItem.getNameProduct());
                    }
                    deliveryService.addProductToShoppingProducts(menuItem2);
                }
            });

            placeOrder.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    deliveryService.createOrder(username);
                    totalPriceLabel.setText("Total price: "+deliveryService.computeTotalPrice());

                    deliveryService.emptyShoppingProducts();

                }
            });

        }

        private void setColorsFonts(){

            this.tablePanel.setBackground(backgroundColor);
            this.operationsPanel.setBackground(backgroundColor);
            this.productsTable.setBackground(tableColor);
            this.titlePanel.setBackground(backgroundColor);
            this.titleLabel.setFont(titleFont);
            this.priceLabel.setFont(font);
            this.reloadTable.setBackground(buttonColor);
            this.reloadTable.setFont(font);
            this.totalPriceLabel.setFont(font);
            this.placeOrder.setFont(font);
            this.placeOrder.setBackground(buttonColor);
        }

        public JScrollPane getScrollPane() {
        return scrollPane;
    }

//    public static void main (String[] argc) throws IOException {
//            JFrame frame = new gui.CreateOrdersWindow("Client :)");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(500,350);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        }

    }

