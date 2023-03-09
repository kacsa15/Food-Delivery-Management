package gui;

import businesslogic.BaseProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import dataaccess.ReadFile;
import dataaccess.Serializatior;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class ClientsWindow extends JFrame {


    private JTable productsTable;
    private MenuItem menuItem = new BaseProduct();
    private JScrollPane scrollPane;
    private JPanel contentPane;
    private JPanel tablePanel;
    private JButton viewProducts;
    private JButton searchButton;
    private JButton createOrderButton;
    private JTextField nameTextField;
    private JTextField ratingTextField;
    private JTextField priceTextField;
    private JTextField caloriesTextField;
    private JTextField fatTextField;
    private JTextField proteinsTextField;
    private JTextField sodiumTextField;
    private JPanel orderPanel;

    private final String username;


    private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
    private final Font comboBoxFont = new Font("Cascadia Mono",0, 10);
    private final Font resultFont = new Font("Cascadia Mono", Font.ITALIC, 14);
    private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 22);
    private final Color buttonColor = new Color(102,204,204);
    private final Color backgroundColor = new Color(192,217,207);
    private final  Color tableColor = new Color(223,234,226);

    private DeliveryService deliveryService;
    private final DefaultTableModel defaultTableModel;
    ArrayList<MenuItem> menuItemArrayList;



    public ClientsWindow(String name, DeliveryService deliveryService,String username) {
        super(name);
        this.username = username;
        this.deliveryService = deliveryService;
        this.defaultTableModel = new DefaultTableModel();
        this.prepareGui();
        this.setColorsFonts();
    }

    private void sendToOtherFrameBtnActionPerformed(java.awt.event.ActionEvent evt, MenuItem menuItem) {
        CreateOrdersWindow otherFrame = new CreateOrdersWindow("Place an order",this,deliveryService,username);

        otherFrame.menuItems.add(menuItem);
        System.out.println(menuItem.getNameProduct() + " " + menuItem.computePrice());
        otherFrame.setVisible(true);
    }

    public void prepareGui(){
        this.setSize(1000, 500);
       // this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.contentPane.add(prepareTablePanel(),constraints);
//
//        constraints.gridx = 0;
//        constraints.gridy = 2;
//        constraints.weightx=0.5;
//        constraints.fill=GridBagConstraints.HORIZONTAL;
//        //JPanel orderPan = new JPanel();
//        //orderPan = prepareTablePanelOrder();
//        this.contentPane.add(prepareTablePanelOrder(),constraints);


        this.setContentPane(this.contentPane);
        createActionListener();
    }

    public void addRow(Object[] row){
        defaultTableModel.addRow(row);
    }

    public void removeRow(){
        defaultTableModel.setRowCount(0);

    }

    private JPanel prepareSearchFieldsPanel(){

        JPanel searchFields = new JPanel();
        searchFields.setBackground(backgroundColor);
        searchFields.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Search for a product by its: ",SwingConstants.RIGHT);
        searchLabel.setFont(resultFont);
        searchFields.add(searchLabel,constraints);


        ///////////  NAME  ///////////

        JPanel nameField = new JPanel();
        nameField.setBackground(backgroundColor);
        nameField.setLayout(new GridBagLayout());
        GridBagConstraints constraintsName = new GridBagConstraints();

        constraintsName.insets = new Insets(5,5,5,5);


        constraintsName.gridx = 0;
        constraintsName.gridy = 0;
        constraintsName.weightx=0.5;
        constraintsName.fill=GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("  Name:  ",SwingConstants.RIGHT);
        nameLabel.setFont(font);
        nameField.add(nameLabel,constraintsName);

        constraintsName.gridx = 1;
        constraintsName.gridy = 0;
        constraintsName.weightx=0.5;
        constraintsName.fill=GridBagConstraints.HORIZONTAL;

        this.nameTextField = new JTextField();
        this.nameTextField.setColumns(5);
        this.nameTextField.setBackground(tableColor);
        nameField.add(this.nameTextField,constraintsName);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        this.nameTextField.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        searchFields.add(nameField,constraints);

        //////// NAME END ////////

        ///////////  RATING  ///////////

        JPanel ratingFieldPanel = new JPanel();
        ratingFieldPanel.setBackground(backgroundColor);
        ratingFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsRating = new GridBagConstraints();

        constraintsRating.insets = new Insets(5,5,5,5);


        constraintsRating.gridx = 0;
        constraintsRating.gridy = 0;
        constraintsRating.weightx=0.5;
        constraintsRating.fill=GridBagConstraints.HORIZONTAL;

        JLabel ratingLabel = new JLabel(" Rating: ",SwingConstants.RIGHT);
        ratingLabel.setFont(font);
        ratingFieldPanel.add(ratingLabel,constraintsRating);

        constraintsRating.gridx = 1;
        constraintsRating.gridy = 0;
        constraintsRating.weightx=0.5;
        constraintsRating.fill=GridBagConstraints.HORIZONTAL;

        this.ratingTextField = new JTextField();
        this.ratingTextField.setColumns(5);
        this.ratingTextField.setBackground(tableColor);
        ratingFieldPanel.add(this.ratingTextField,constraintsRating);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(ratingFieldPanel,constraints);

        //////// RATING END ////////


        ///////////  CALORIES  ///////////

        JPanel caloriesFieldPanel = new JPanel();
        caloriesFieldPanel.setBackground(backgroundColor);
        caloriesFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsCalories = new GridBagConstraints();

        constraintsCalories.insets = new Insets(5,5,5,5);


        constraintsCalories.gridx = 0;
        constraintsCalories.gridy = 0;
        constraintsCalories.weightx=0.5;
        constraintsCalories.fill=GridBagConstraints.HORIZONTAL;

        JLabel caloriesLabel = new JLabel("Calories:",SwingConstants.RIGHT);
        caloriesLabel.setFont(font);
        caloriesFieldPanel.add(caloriesLabel,constraintsCalories);

        constraintsCalories.gridx = 1;
        constraintsCalories.gridy = 0;
        constraintsCalories.weightx=0.5;
        constraintsCalories.fill=GridBagConstraints.HORIZONTAL;

        this.caloriesTextField = new JTextField();
        this.caloriesTextField.setColumns(5);
        this.caloriesTextField.setBackground(tableColor);
        caloriesFieldPanel.add(this.caloriesTextField,constraintsCalories);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(caloriesFieldPanel,constraints);

        //////// CALORIES END ////////


        ///////////  PROTEINS  ///////////

        JPanel proteinsFieldPanel = new JPanel();
        proteinsFieldPanel.setBackground(backgroundColor);
        proteinsFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsProteins = new GridBagConstraints();

        constraintsProteins.insets = new Insets(5,5,5,5);


        constraintsProteins.gridx = 0;
        constraintsProteins.gridy = 0;
        constraintsProteins.weightx=0.5;
        constraintsProteins.fill=GridBagConstraints.HORIZONTAL;

        JLabel proteinsLabel = new JLabel("Proteins:",SwingConstants.RIGHT);
        proteinsLabel.setFont(font);
        proteinsFieldPanel.add(proteinsLabel,constraintsProteins);

        constraintsProteins.gridx = 1;
        constraintsProteins.gridy = 0;
        constraintsProteins.weightx=0.5;
        constraintsProteins.fill=GridBagConstraints.HORIZONTAL;

        this.proteinsTextField = new JTextField();
        this.proteinsTextField.setColumns(5);
        this.proteinsTextField.setBackground(tableColor);
        proteinsFieldPanel.add(this.proteinsTextField,constraintsProteins);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(proteinsFieldPanel,constraints);

        //////// PROTEINS END ////////


        ///////////  FAT  ///////////

        JPanel fatFieldPanel = new JPanel();
        fatFieldPanel.setBackground(backgroundColor);
        fatFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsFat = new GridBagConstraints();

        constraintsFat.insets = new Insets(5,5,5,5);


        constraintsFat.gridx = 0;
        constraintsFat.gridy = 0;
        constraintsFat.weightx=0.5;
        constraintsFat.fill=GridBagConstraints.HORIZONTAL;

        JLabel fatLabel = new JLabel("Fat:     ",SwingConstants.RIGHT);
        fatLabel.setFont(font);
        fatFieldPanel.add(fatLabel,constraintsFat);

        constraintsFat.gridx = 1;
        constraintsFat.gridy = 0;
        constraintsFat.weightx=0.5;
        constraintsFat.fill=GridBagConstraints.HORIZONTAL;

        this.fatTextField = new JTextField();
        this.fatTextField.setColumns(5);
        this.fatTextField.setBackground(tableColor);
        fatFieldPanel.add(this.fatTextField,constraintsFat);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(fatFieldPanel,constraints);

        //////// FAT END ////////

        ///////////  SODIUM  ///////////

        JPanel sodiumFieldPanel = new JPanel();
        sodiumFieldPanel.setBackground(backgroundColor);
        sodiumFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsSodium = new GridBagConstraints();

        constraintsSodium.insets = new Insets(5,5,5,5);


        constraintsSodium.gridx = 0;
        constraintsSodium.gridy = 0;
        constraintsSodium.weightx=0.5;
        constraintsSodium.fill=GridBagConstraints.HORIZONTAL;

        JLabel sodiumLabel = new JLabel("Sodium:  ",SwingConstants.RIGHT);
        sodiumLabel.setFont(font);
        sodiumFieldPanel.add(sodiumLabel,constraintsSodium);

        constraintsSodium.gridx = 1;
        constraintsSodium.gridy = 0;
        constraintsSodium.weightx=0.5;
        constraintsSodium.fill=GridBagConstraints.HORIZONTAL;

        this.sodiumTextField = new JTextField();
        this.sodiumTextField.setColumns(5);
        this.sodiumTextField.setBackground(tableColor);
        sodiumFieldPanel.add(this.sodiumTextField,constraintsSodium);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(sodiumFieldPanel,constraints);

        //////// SODIUM END ////////

        ///////////  PRICE  ///////////

        JPanel priceFieldPanel = new JPanel();
        priceFieldPanel.setBackground(backgroundColor);
        priceFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsPrice = new GridBagConstraints();

        constraintsPrice.insets = new Insets(5,5,5,5);


        constraintsPrice.gridx = 0;
        constraintsPrice.gridy = 0;
        constraintsPrice.weightx=0.5;
        constraintsPrice.fill=GridBagConstraints.HORIZONTAL;

        JLabel priceLabel = new JLabel("Price:   ",SwingConstants.RIGHT);
        priceLabel.setFont(font);
        priceFieldPanel.add(priceLabel,constraintsPrice);

        constraintsPrice.gridx = 1;
        constraintsPrice.gridy = 0;
        constraintsPrice.weightx=0.5;
        constraintsPrice.fill=GridBagConstraints.HORIZONTAL;

        this.priceTextField = new JTextField();
        this.priceTextField.setColumns(5);
        this.priceTextField.setBackground(tableColor);
        priceFieldPanel.add(this.priceTextField,constraintsPrice);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(priceFieldPanel,constraints);

        //////// PRICE END ////////

        ///////////  BUTTONS  ///////////

        JPanel buttonsFieldPanel = new JPanel();
        buttonsFieldPanel.setBackground(backgroundColor);
        buttonsFieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsButtons = new GridBagConstraints();

        constraintsButtons.insets = new Insets(5,5,5,5);


        constraintsButtons.gridx = 0;
        constraintsButtons.gridy = 0;
        constraintsButtons.weightx=0.5;
        constraintsButtons.fill=GridBagConstraints.HORIZONTAL;

        this.searchButton = new JButton("Search");
        this.searchButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        buttonsFieldPanel.add(this.searchButton,constraintsButtons);


        constraintsButtons.gridx = 0;
        constraintsButtons.gridy = 1;
        constraintsButtons.weightx=0.5;
        constraintsButtons.fill=GridBagConstraints.HORIZONTAL;

        this.viewProducts = new JButton("Add product to order");
        //this.viewProducts.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        buttonsFieldPanel.add(this.viewProducts,constraintsButtons);

        constraintsButtons.gridx = 0;
        constraintsButtons.gridy = 2;
        constraintsButtons.weightx=0.5;
        constraintsButtons.fill=GridBagConstraints.HORIZONTAL;

        this.createOrderButton = new JButton("Create order");
        //this.createOrderButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        buttonsFieldPanel.add(this.createOrderButton,constraintsButtons);


        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        searchFields.add(buttonsFieldPanel,constraints);


        //////// BUTTONS END ////////



        return searchFields;
    }

    private JPanel prepareTablePanel(){

        tablePanel = new JPanel();
        this.tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

       tablePanel.add(prepareSearchFieldsPanel(),constraints);

        Serializatior serializatior = new Serializatior();
        ReadFile readFile = new ReadFile();

        HashSet<MenuItem> listOfItems = new HashSet<>();
        try {
            listOfItems = readFile.readProducts("C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\products.csv");

            //productsTable = makeTable(listOfItems);
        } catch (IOException e){//| IntrospectionException | InvocationTargetException | IllegalAccessException e){// | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //showMessageDialog(null,new JScrollPane(productsTable),"Clients Table", PLAIN_MESSAGE );


        String[] columns = {"Name","Rating","Calories","Protein","Fat","Sodium","Price"};
        menuItemArrayList = new ArrayList<>(listOfItems);
        defaultTableModel.setColumnIdentifiers(columns);
        Object[] objects = new Object[7];
        Iterator<MenuItem> iterator = menuItemArrayList.iterator();
        while(iterator.hasNext()){
            MenuItem currentItem = iterator.next();
            objects[0] = currentItem.getNameProduct();
            objects[1] = currentItem.computeRating();
            objects[2] = currentItem.computeCalories();
            objects[3] = currentItem.computeProteins();
            objects[4] = currentItem.computeFats();
            objects[5] = currentItem.computeSodium();
            objects[6] = currentItem.computePrice();
            defaultTableModel.addRow(objects);
        }

        productsTable = new JTable(defaultTableModel);
        scrollPane = new JScrollPane();
        productsTable.getColumnModel().getColumn(0).setPreferredWidth(450);
        productsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        productsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        productsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        productsTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        productsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        productsTable.getColumnModel().getColumn(6).setPreferredWidth(70);
        scrollPane.setViewportView(productsTable);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;


        tablePanel.add(scrollPane,constraints);

        return tablePanel;
        //this.contentPane.add(tablePanel);
    }

    private JPanel prepareTablePanelOrder(){

        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.viewProducts = new JButton("Add product to order");
        //this.viewProducts.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.orderPanel.add(this.viewProducts,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.createOrderButton = new JButton("Create order");
        //this.createOrderButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.orderPanel.add(this.createOrderButton,constraints);


        Serializatior serializatior = new Serializatior();
        ReadFile readFile = new ReadFile();

        HashSet<MenuItem> listOfItems = new HashSet<>();
        try {
            listOfItems = readFile.readProducts("C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\products.csv");

            //productsTable = makeTable(listOfItems);
        } catch (IOException e){//| IntrospectionException | InvocationTargetException | IllegalAccessException e){// | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String[] columns = {"Name","Rating","Calories","Protein","Fat","Sodium","Price"};
        ArrayList<MenuItem> menuItemArrayList = new ArrayList<>(listOfItems);
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(columns);
        Object[] objects = new Object[7];
        Iterator<MenuItem> iterator = menuItemArrayList.iterator();
        while(iterator.hasNext()){
            MenuItem currentItem = iterator.next();
            objects[0] = currentItem.getNameProduct();
            objects[1] = currentItem.computeRating();
            objects[2] = currentItem.computeCalories();
            objects[3] = currentItem.computeProteins();
            objects[4] = currentItem.computeFats();
            objects[5] = currentItem.computeSodium();
            objects[6] = currentItem.computePrice();
            defaultTableModel.addRow(objects);
        }

        JTable orderTable = new JTable(defaultTableModel);
        scrollPane = new JScrollPane();
        //scrollPane.setBounds(250,100,100,300);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(450);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        orderTable.getColumnModel().getColumn(6).setPreferredWidth(70);
        scrollPane.setViewportView(orderTable);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        orderPanel.add(scrollPane,constraints);

       // this.contentPane.add(this.orderPanel);
        return orderPanel;
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
       // this.operationsPanel.setBackground(buttonColor);

        this.productsTable.setBackground(tableColor);

        this.searchButton.setBackground(buttonColor);
        this.searchButton.setFont(font);
        this.searchButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));


        this.createOrderButton.setFont(font);
        this.createOrderButton.setBackground(buttonColor);
        this.createOrderButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.viewProducts.setBackground(buttonColor);
        this.viewProducts.setFont(font);
        this.viewProducts.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));


    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getRatingTextField() {
        return ratingTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JTextField getCaloriesTextField() {
        return caloriesTextField;
    }

    public JTextField getFatTextField() {
        return fatTextField;
    }

    public JTextField getProteinsTextField() {
        return proteinsTextField;
    }

    public JTextField getSodiumTextField() {
        return sodiumTextField;
    }

    public int getMenuIndex(){
        System.out.println("megvan");
        return productsTable.getSelectedRow();

    }

    public void createActionListener(){

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String nameProduct = getNameTextField().getText();
                int fat,calories,proteins,sodium,price;
                double rating;

                //fat
                if(getFatTextField().getText().equals("") || Integer.parseInt(getFatTextField().getText()) < 0){
                    fat = -1;
                }else{
                    fat = Integer.parseInt(getFatTextField().getText());
                }

                //calories
                if(getCaloriesTextField().getText().equals("") || Integer.parseInt(getCaloriesTextField().getText()) < 0){
                    calories = -1;
                }else{
                    calories = Integer.parseInt(getCaloriesTextField().getText());
                }

                //proteins
                if(getProteinsTextField().getText().equals("") || Integer.parseInt(getProteinsTextField().getText()) < 0){
                    proteins = -1;
                }else{
                    proteins = Integer.parseInt(getProteinsTextField().getText());
                }

                //sodium
                if(getSodiumTextField().getText().equals("") || Integer.parseInt(getSodiumTextField().getText()) < 0){
                    sodium = -1;
                }else{
                    sodium = Integer.parseInt(getSodiumTextField().getText());
                }

                //price
                if(getPriceTextField().getText().equals("") || Integer.parseInt(getPriceTextField().getText()) < 0){
                    price = -1;
                }else{
                    price = Integer.parseInt(getPriceTextField().getText());
                }

                //rating
                if(getRatingTextField().getText().equals("") || Double.parseDouble(getRatingTextField().getText()) < 0){
                    rating = -1;
                }else{
                    rating = Double.parseDouble(getFatTextField().getText());
                }

                menuItemArrayList = (ArrayList<MenuItem>) deliveryService.searchForProduct(nameProduct,rating,calories,proteins,fat,sodium,price);
                removeRow();
                for(MenuItem menuItem: menuItemArrayList){
                    addRow(new Object[]{
                            menuItem.getNameProduct(),
                            menuItem.computeRating(),
                            menuItem.computeCalories(),
                            menuItem.computeProteins(),
                            menuItem.computeFats(),
                            menuItem.computeSodium(),
                            menuItem.computePrice()
                    });
                }
            }
        });

        createOrderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sendToOtherFrameBtnActionPerformed(e,menuItem);

            }
        });

//        viewProducts.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//
//            }
//        });

        productsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                int column = 0;
                int row = productsTable.getSelectedRow();
                menuItem.setNameProduct(productsTable.getModel().getValueAt(row,0).toString());
                menuItem.setRating(Double.parseDouble(productsTable.getModel().getValueAt(row,1).toString()));
                menuItem.setCalories(Integer.parseInt(productsTable.getModel().getValueAt(row,2).toString()));
                menuItem.setProteins(Integer.parseInt(productsTable.getModel().getValueAt(row,3).toString()));
                menuItem.setFats(Integer.parseInt(productsTable.getModel().getValueAt(row,4).toString()));
                menuItem.setSodium(Integer.parseInt(productsTable.getModel().getValueAt(row,5).toString()));
                menuItem.setPriceProduct(Integer.parseInt(productsTable.getModel().getValueAt(row,6).toString()));

                //System.out.println(menuItem.getNameProduct());
            }
        });
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }



}

