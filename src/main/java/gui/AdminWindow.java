package gui;

import businesslogic.BaseProduct;
import businesslogic.CompositeProduct;
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
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class AdminWindow extends JFrame {

        private JPanel panel1;
        private JTable productsTable;
        private JButton deleteButton;
        private JButton modifyButton;
        private JButton addProductButton;
        private JButton createComposedProductButton;
        private JComboBox generateRaportComboBox;
        private JButton generateReportButton;
        private MenuItem menuItem = new BaseProduct();
        private JScrollPane scrollPane;
        private JPanel contentPane;
        private JPanel tablePanel;
        private JPanel buttonsPanel;
        private JTextField nameTextField;
        private JTextField priceTextField;
        private JTextField ratingTextField;
        private JTextField fatTextField;
        private JTextField sodiumTextField;
        private JTextField caloriesTextField;
        private JTextField proteinsTextField;
        private JTextField compositeProductTitle;


        private final Font font = new Font("Cascadia Mono", Font.ITALIC, 12);
        private final Font comboBoxFont = new Font("Cascadia Mono",0, 10);
        private final Font titleFont = new Font("Cascadia Mono", Font.HANGING_BASELINE, 22);
        private final Color buttonColor = new Color(102,204,204);
        private final Color backgroundColor = new Color(192,217,207);
        private final  Color tableColor = new Color(223,234,226);

        private DeliveryService deliveryService;
        private ArrayList<MenuItem> menuItemList = new ArrayList<>();
        private DefaultTableModel defaultTableModel;




    public AdminWindow(String name, DeliveryService deliveryService) {
        super(name);

        this.deliveryService = deliveryService;
        defaultTableModel = new DefaultTableModel();
        this.prepareGui();
        this.setColorsFonts();

    }

    public void prepareGui(){
        this.setSize(1000, 500);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(2,1));
        this.prepareTablePanel();
        this.prepareButtonsPanel();
        this.setContentPane(this.contentPane);
        createActionListeners();


    }

    public void addRow(Object[] row){
            defaultTableModel.addRow(row);
    }

    public void removeRow(int rowIndex){
            defaultTableModel.removeRow(rowIndex);
    }

    public  void printTable(ArrayList<MenuItem> menuItemArrayList){
            String[] columns = {"Name","Rating","Calories","Protein","Fat","Sodium","Price"};

//            defaultTableModel = new DefaultTableModel();

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

            productsTable.setModel(defaultTableModel);

            productsTable.getColumnModel().getColumn(0).setPreferredWidth(450);
            productsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            productsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            productsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
            productsTable.getColumnModel().getColumn(4).setPreferredWidth(60);
            productsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
            productsTable.getColumnModel().getColumn(6).setPreferredWidth(70);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    private void prepareTablePanel(){

        tablePanel = new JPanel(new GridLayout(2,0));

        ArrayList<MenuItem> menuItemArrayList = deliveryService.getMenuItems();

        productsTable = new JTable();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(productsTable);
        printTable(menuItemArrayList);

        JPanel auxPanel = new JPanel(new GridLayout(2,2));
        JLabel titlePanel = new JLabel("Admin",SwingConstants.CENTER);
        titlePanel.setFont(titleFont);
        auxPanel.add(titlePanel);
        auxPanel.setBackground(backgroundColor);
        this.tablePanel.add(auxPanel);
        this.tablePanel.add( this.scrollPane);


        this.contentPane.add(tablePanel);
    }

        private void prepareButtonsPanel(){

        this.buttonsPanel = new JPanel();
        this.buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5,5,5,5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.addProductButton = new JButton("Add product");
        this.addProductButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.buttonsPanel.add(this.addProductButton,constraints);

        // add product text fields
            JPanel textFieldsPanel = new JPanel();
            textFieldsPanel.setBackground(backgroundColor);
            textFieldsPanel.setLayout(new GridBagLayout());
            GridBagConstraints constraintsTextField = new GridBagConstraints();

            constraintsTextField.insets = new Insets(5,5,5,5);

            constraintsTextField.gridx = 1;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.nameTextField = new JTextField();
            this.nameTextField.setColumns(2);
            this.nameTextField.setText("Name");
            textFieldsPanel.add(this.nameTextField,constraintsTextField);

            constraintsTextField.gridx = 2;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.ratingTextField = new JTextField();
            this.ratingTextField.setColumns(2);
            this.ratingTextField.setText("Rating");
            textFieldsPanel.add(this.ratingTextField,constraintsTextField);

            constraintsTextField.gridx = 3;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.caloriesTextField = new JTextField();
            this.caloriesTextField.setColumns(2);
            this.caloriesTextField.setText("Calories");
            textFieldsPanel.add(this.caloriesTextField,constraintsTextField);

            constraintsTextField.gridx = 4;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.proteinsTextField = new JTextField();
            this.proteinsTextField.setColumns(2);
            this.proteinsTextField.setText("Proteins");
            textFieldsPanel.add(this.proteinsTextField,constraintsTextField);

            constraintsTextField.gridx = 5;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.fatTextField = new JTextField();
            this.fatTextField.setColumns(2);
            this.fatTextField.setText("Fat");
            textFieldsPanel.add(this.fatTextField,constraintsTextField);

            constraintsTextField.gridx = 6;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.sodiumTextField = new JTextField();
            this.sodiumTextField.setColumns(2);
            this.sodiumTextField.setText("Sodium");
            textFieldsPanel.add(this.sodiumTextField,constraintsTextField);

            constraintsTextField.gridx = 7;
            constraintsTextField.gridy = 0;
            constraintsTextField.weightx=0.5;
            constraintsTextField.fill=GridBagConstraints.HORIZONTAL;
            this.priceTextField = new JTextField();
            this.priceTextField.setColumns(2);
            this.priceTextField.setText("Price");
            textFieldsPanel.add(this.priceTextField,constraintsTextField);



            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;
            this.buttonsPanel.add(textFieldsPanel,constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.modifyButton = new JButton("Modify");
        this.modifyButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.buttonsPanel.add(this.modifyButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.deleteButton = new JButton("Delete");
        this.modifyButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.buttonsPanel.add(this.deleteButton,constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.createComposedProductButton = new JButton("Create new product");
        this.modifyButton.setActionCommand("ADDITION");
        //this.addProductButton.addActionListener(this.controller);
        this.buttonsPanel.add(this.createComposedProductButton,constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.weightx=0.5;
            constraints.fill=GridBagConstraints.HORIZONTAL;
        this.compositeProductTitle = new JTextField();
        //this.compositeProductTitle.setColumns(5);
        this.compositeProductTitle.setPreferredSize(new Dimension( 5, 18));
        this.compositeProductTitle.setText("Composite product's name");
        this.compositeProductTitle.setFont(comboBoxFont);
        JPanel compositeTextPanel = new JPanel();
        compositeTextPanel.setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

            constr.insets = new Insets(5,5,5,5);

            constr.gridx = 0;
            constr.gridy = 0;
            constr.weightx=0.5;
            constr.fill=GridBagConstraints.HORIZONTAL;
        compositeTextPanel.setBackground(backgroundColor);
        compositeTextPanel.add(compositeProductTitle,constr);
        JPanel auxPanel = new JPanel();
            constr.gridx = 1;
            constr.gridy = 0;
            constr.weightx=0.5;
            constr.fill=GridBagConstraints.HORIZONTAL;
            auxPanel.setBackground(backgroundColor);
            compositeTextPanel.add(auxPanel,constr);

        this.buttonsPanel.add(compositeTextPanel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        DefaultComboBoxModel<String> adminClientEmployeeModel = new DefaultComboBoxModel<String>();
        adminClientEmployeeModel.addElement("Time interval of the orders");
        adminClientEmployeeModel.addElement("Products ordered more than a specified number of times");
        adminClientEmployeeModel.addElement("Clients that have ordered more than a specified number of times + value of order is higher than a specified amount");
        adminClientEmployeeModel.addElement("The products ordered within a specified day with the number of times they have been ordered");
        this.generateRaportComboBox = new JComboBox<String>(adminClientEmployeeModel);
        this.generateRaportComboBox.setActionCommand("ADDITION");
        this.generateRaportComboBox.setFont(comboBoxFont);
        //this.addProductButton.addActionListener(this.controller);
        this.buttonsPanel.add(this.generateRaportComboBox,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx=0.5;
        constraints.fill=GridBagConstraints.HORIZONTAL;

        this.generateReportButton = new JButton("     Generate report     ");
        this.generateReportButton.setActionCommand("ADDITION");
        JPanel raportPanel = new JPanel();
        raportPanel.add(generateReportButton);
        raportPanel.setBackground(backgroundColor);

        this.buttonsPanel.add(raportPanel,constraints);


        this.contentPane.add(buttonsPanel);
    }

        private void setColorsFonts(){

        this.tablePanel.setBackground(backgroundColor);
        this.buttonsPanel.setBackground(backgroundColor);
        this.productsTable.setBackground(tableColor);
        this.scrollPane.setBackground(buttonColor);
        this.proteinsTextField.setBackground(tableColor);

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = buttonColor;
            }
        });

        this.generateReportButton.setBackground(buttonColor);
        this.generateReportButton.setFont(font);
        this.generateReportButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.addProductButton.setBackground(buttonColor);
        this.addProductButton.setFont(font);
        this.addProductButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.deleteButton.setBackground(buttonColor);
        this.deleteButton.setFont(font);
        this.deleteButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.modifyButton.setBackground(buttonColor);
        this.modifyButton.setFont(font);
        this.modifyButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.createComposedProductButton.setBackground(buttonColor);
        this.createComposedProductButton.setFont(font);
        this.createComposedProductButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,buttonColor));

        this.productsTable.getTableHeader().setOpaque(false);
        this.productsTable.getTableHeader().setBackground(buttonColor);
        this.productsTable.setFont(comboBoxFont);

        this.nameTextField.setFont(comboBoxFont);
        this.ratingTextField.setFont(comboBoxFont);
        this.priceTextField.setFont(comboBoxFont);
        this.proteinsTextField.setFont(comboBoxFont);
        this.caloriesTextField.setFont(comboBoxFont);
        this.fatTextField.setFont(comboBoxFont);
        this.sodiumTextField.setFont(comboBoxFont);

        this.nameTextField.setBackground(tableColor);
        this.ratingTextField.setBackground(tableColor);
        this.sodiumTextField.setBackground(tableColor);
        this.fatTextField.setBackground(tableColor);
        this.caloriesTextField.setBackground(tableColor);
        this.productsTable.setBackground(tableColor);
        this.priceTextField.setBackground(tableColor);

        this.compositeProductTitle.setBackground(tableColor);

        this.generateRaportComboBox.setBackground(tableColor);


    }

        // action listeners for some buttons/table mouse click
        public void createActionListeners(){
        //prepareGui();

            addProductButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    MenuItem menuItem = new BaseProduct(getNameTextField().getText(),
                            Integer.parseInt(getPriceTextField().getText()),
                            Double.parseDouble(getRatingTextField().getText()),
                            Integer.parseInt(getFatTextField().getText()),
                            Integer.parseInt(getProteinsTextField().getText()),
                            Integer.parseInt(getSodiumTextField().getText()),
                            Integer.parseInt(getCaloriesTextField().getText())
                    );

                    System.out.println(menuItem.getNameProduct());
                    System.out.println(menuItem.computePrice());

                    deliveryService.addItem(menuItem, new AdminWindow("Admin", deliveryService));
                    addRow(new Object[]{
                            getNameTextField().getText(),
                            getPriceTextField().getText(),
                            getRatingTextField().getText(),
                            getFatTextField().getText(),
                            getProteinsTextField().getText(),
                            getSodiumTextField().getText(),
                            getCaloriesTextField().getText()
                    });


                }
            });

            deleteButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    deliveryService.deleteItem(getMenuIndex());
                    removeRow(getMenuIndex());
                    menuItemList = new ArrayList<>();
                }
            });

            modifyButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    //getMouseListeners();
                    deliveryService.modifyItems(menuItem,getMenuIndex());


                }
            });

            createComposedProductButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    deliveryService.createCompositeProduct(getCompositeProductTitle().getText(), menuItemList);
                    printTable(deliveryService.getMenuItems());
                    menuItemList = new ArrayList<>();

                }
            });

            generateReportButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    String selectedReport = String.valueOf(generateRaportComboBox.getSelectedItem());

                    switch (selectedReport){
                        case "Time interval of the orders":
                            Report1Window report1Window = new Report1Window("Report1",deliveryService);
                            report1Window.setSize(350,250);
                            report1Window.setLocationRelativeTo(null);
                            report1Window.setVisible(true);
                            break;
                        case "Products ordered more than a specified number of times":
                            Report2Window report2Window = new Report2Window("Report2",deliveryService);
                            report2Window.setSize(350,250);
                            report2Window.setLocationRelativeTo(null);
                            report2Window.setVisible(true);
                            break;
                        case "The products ordered within a specified day with the number of times they have been ordered":
                            Report3Window report3Window = new Report3Window("Report3",deliveryService);
                            report3Window.setSize(350,250);
                            report3Window.setLocationRelativeTo(null);
                            report3Window.setVisible(true);
                            break;
                        case "Clients that have ordered more than a specified number of times + value of order is higher than a specified amount":
                            Report4Window report4Window = new Report4Window("Report4",deliveryService);
                            report4Window.setSize(350,250);
                            report4Window.setLocationRelativeTo(null);
                            report4Window.setVisible(true);
                            break;
                    }

                }
            });

            productsTable.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = productsTable.getSelectedRow();
                    MenuItem menuItem = new BaseProduct();
                    menuItem.setNameProduct(productsTable.getModel().getValueAt(row,0).toString());
                    menuItem.setRating(Double.parseDouble(productsTable.getModel().getValueAt(row,1).toString()));
                    menuItem.setCalories(Integer.parseInt(productsTable.getModel().getValueAt(row,2).toString()));
                    menuItem.setProteins(Integer.parseInt(productsTable.getModel().getValueAt(row,3).toString()));
                    menuItem.setFats(Integer.parseInt(productsTable.getModel().getValueAt(row,4).toString()));
                    menuItem.setSodium(Integer.parseInt(productsTable.getModel().getValueAt(row,5).toString()));
                    menuItem.setPriceProduct(Integer.parseInt(productsTable.getModel().getValueAt(row,6).toString()));

                    menuItemList.add(menuItem);

                }
            });
        }

        public int getMenuIndex(){
            System.out.println("megvan");
        return productsTable.getSelectedRow();

    }

        public JTable makeTable(HashSet<MenuItem> listOfItems) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

            ArrayList<MenuItem> menuItems = new ArrayList<>(listOfItems);

            if(menuItems!=null && menuItems.size() > 0){
                int i=0;
                String[] columns = new String[menuItems.get(0).getClass().getDeclaredFields().length];
                for(Field field : menuItems.get(0).getClass().getDeclaredFields()){
                    columns[i] = field.getName();
                    i++;
                }

                int j=0;
                Object[][] table = new Object[menuItems.size()+1][i];
                for(MenuItem object : menuItems){
                    int k=0;
                    for(Field field : object.getClass().getDeclaredFields()){
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,object.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        table[j][k]=method.invoke(object);
                        k++;
                    }
                    j++;
                }
                return new JTable(table,columns);
            }
            return new JTable(1,0);
        }

        public JTextField getNameTextField() {
        return nameTextField;
    }

        public JTextField getPriceTextField() {
        return priceTextField;
    }

        public JTextField getRatingTextField() {
        return ratingTextField;
    }

        public JTextField getFatTextField() {
        return fatTextField;
    }

        public JTextField getSodiumTextField() {
        return sodiumTextField;
    }

        public JTextField getCaloriesTextField() {
        return caloriesTextField;
    }

        public JTextField getProteinsTextField() {
        return proteinsTextField;
    }

        public JTextField getCompositeProductTitle() {
        return compositeProductTitle;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }


    }

