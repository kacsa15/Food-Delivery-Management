package gui;
/**LEHET HOGY EZT UJRA KENE CSINALNI!!!!!*/
import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import dataaccess.ReadFile;
import dataaccess.Serializatior;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginFormWindow extends JFrame {

    private JPanel panel1;
    private JLabel logIn;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;
    private JComboBox adminClientEmployee;
    private JButton logInButton;
    private JButton registerButton;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    ArrayList<User> admins = new ArrayList<>();
    ArrayList<User> clients = new ArrayList<>();
    ArrayList<User> employees = new ArrayList<>();
    String userType;
    private DeliveryService deliveryService;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getUserNameTextField() {
        return userNameTextField;
    }

    public void setUserNameTextField(JTextField userNameTextField) {
        this.userNameTextField = userNameTextField;
    }

    public JPasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(JPasswordField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public JComboBox getAdminClientEmployee() {
        return adminClientEmployee;
    }

    public void setAdminClientEmployee(JComboBox adminClientEmployee) {
        this.adminClientEmployee = adminClientEmployee;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public void setLogInButton(JButton logInButton) {
        this.logInButton = logInButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(JButton registerButton) {
        this.registerButton = registerButton;
    }


    public User getInput() {

        User currentUser = new User();

        currentUser.setUserName(userNameTextField.getText());
        currentUser.setPassword(String.valueOf(passwordTextField.getPassword()));
        userType = (String) adminClientEmployee.getSelectedItem();

        return currentUser;
    }

    public void createActionListeners() {

        logInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                User user = getInput();


                if (userType.equals("Admin") &&
                        user.getUserName().equals("admin") && user.getPassword().equals("admin")) {
                    //successful login --> ADMIN
                    JFrame frame = new AdminWindow("Admin :)", deliveryService);
                    frame.setSize(950, 500);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    if (userType.equals("Admin") &&
                            (!user.getUserName().equals("admin") || !user.getPassword().equals("admin"))) {
                        showMessageDialog(null, new JLabel("Incorrect username or password :("), "Invalid input for Admin", JOptionPane.ERROR_MESSAGE);

                    }
                }

                if (userType.equals("Employee") &&
                        user.getUserName().equals("employee") && user.getPassword().equals("employee")) {
                    //successful login --> Employee
                    EmployeeWindow frame = new EmployeeWindow("Employee :)", deliveryService);
                    deliveryService.addObserver(frame);
                    frame.setSize(950, 500);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    if (userType.equals("Employee") &&
                            (!user.getUserName().equals("employee") || !user.getPassword().equals("employee"))) {
                        showMessageDialog(null, new JLabel("Incorrect username or password :("), "Invalid input for Employee", JOptionPane.ERROR_MESSAGE);

                    }
                }

                if (userType.equals("Client")) {

                    try {
                        String path = "C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\clients.txt";

                        FileReader fr = new FileReader(path);
                        BufferedReader br = new BufferedReader(fr);
                        String line, userName, password;
                        boolean isLoginSuccess = false;
                        while ((line = br.readLine()) != null) {
                            userName = line.split(" ")[0].toLowerCase();
                            password = line.split(" ")[1].toLowerCase();
                            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                                isLoginSuccess = true;
                                //successful login --> Client
                                JFrame frame = new ClientsWindow("Client :)", deliveryService, userName);
                                frame.setSize(950, 500);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                                break;
                            }
                        }
                        if (!isLoginSuccess) {
                            JOptionPane.showMessageDialog(null, "USERNAME/PASSWORD WRONG", "WARNING!!", JOptionPane.WARNING_MESSAGE);
                        }
                        fr.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                User user = getInput();


                if (userType.equals("Admin")) {
                    showMessageDialog(null, new JLabel("You cannot register another admin, please login with the existing one"), "Invalid input for Admin", JOptionPane.ERROR_MESSAGE);
                }

                if (userType.equals("Employee")) {
                    showMessageDialog(null, new JLabel("You cannot register another employee, please login with the existing one"), "Invalid input for Employee", JOptionPane.ERROR_MESSAGE);
                }


                if (userType.equals("Client")) {

                    try {
                        String path = "C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\clients.txt";

                        FileReader fr = new FileReader(path);
                        BufferedReader br = new BufferedReader(fr);
                        String line, userName, password;
                        boolean isLoginSuccess = false;
                        while ((line = br.readLine()) != null) {
                            userName = line.split(" ")[0].toLowerCase();
                            password = line.split(" ")[1].toLowerCase();
                            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                                isLoginSuccess = true;
                                break;
                            }
                        }
                        if (isLoginSuccess) {
                            JOptionPane.showMessageDialog(null, "There already is a client with this username", "WARNING!!", JOptionPane.WARNING_MESSAGE);
                        } else {
                            FileWriter fileWritter = new FileWriter(path, true);
                            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                            bufferWritter.write(user.getUserName() + " " + user.getPassword() + "\n");
                            bufferWritter.close();
                            fileWritter.close();
                        }
                        fr.close();
                        showMessageDialog(null, new JLabel("Successful registration"), "Register a client", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });
    }

    public String getUserName() {
        User user = getInput();
        return user.getUserName();
    }

    public LoginFormWindow() {

        $$$setupUI$$$();

        Serializatior serializator = new Serializatior();
        HashMap<Order, ArrayList<MenuItem>> arrayListHashMap = new HashMap<>();
        ReadFile readCSV = new ReadFile();
        HashSet<MenuItem> menuItemList = null;
        try {
            menuItemList = readCSV.readProducts("C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\products.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MenuItem> menuItems = new ArrayList<>(menuItemList);
        List<User> personList = new ArrayList<>();


        String path = "C:\\Users\\ASUS\\Desktop\\PT2022_30424_SasuSimon_Kinga_Assignment_4\\clients.txt";

        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line, userName, password;
        line = "";
        boolean isLoginSuccess = false;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            userName = line.split(" ")[0].toLowerCase();
            password = line.split(" ")[1].toLowerCase();
            personList.add(new User(userName, password, 0));
        }


        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<MenuItem> shopping = new ArrayList<>();
        HashMap<Order, ArrayList<MenuItem>> orderHashMap = new HashMap<>();
        deliveryService = new DeliveryService(orders, (ArrayList<User>) personList, (ArrayList<MenuItem>) menuItems, shopping, orderHashMap);

        try {
            deliveryService = serializator.deserialize();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        JFrame frame = new JFrame("Login:)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setSize(310, 275);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createActionListeners();

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        DefaultComboBoxModel<String> adminClientEmployeeModel = new DefaultComboBoxModel<String>();
        adminClientEmployeeModel.addElement("Admin");
        adminClientEmployeeModel.addElement("Employee");
        adminClientEmployeeModel.addElement("Client");

        adminClientEmployee = new JComboBox<String>(adminClientEmployeeModel);


    }

    public static <T> T getSelectedItemCombo(JComboBox<T> comboBox) {
        int index = comboBox.getSelectedIndex();
        return comboBox.getItemAt(index);
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-4138545));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 16, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-4138545));
        panel2.setEnabled(true);
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        userNameTextField = new JTextField();
        userNameTextField.setBackground(new Color(-2102558));
        userNameTextField.setForeground(new Color(-16777216));
        panel2.add(userNameTextField, new com.intellij.uiDesigner.core.GridConstraints(9, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordTextField = new JPasswordField();
        passwordTextField.setBackground(new Color(-2102558));
        panel2.add(passwordTextField, new com.intellij.uiDesigner.core.GridConstraints(10, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        userNameLabel = new JLabel();
        userNameLabel.setBackground(new Color(-4138545));
        Font userNameLabelFont = this.$$$getFont$$$("Cascadia Mono", Font.ITALIC, -1, userNameLabel.getFont());
        if (userNameLabelFont != null) userNameLabel.setFont(userNameLabelFont);
        userNameLabel.setForeground(new Color(-16382458));
        userNameLabel.setText("Username");
        panel2.add(userNameLabel, new com.intellij.uiDesigner.core.GridConstraints(9, 3, 1, 9, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordLabel = new JLabel();
        Font passwordLabelFont = this.$$$getFont$$$("Cascadia Mono", Font.ITALIC, -1, passwordLabel.getFont());
        if (passwordLabelFont != null) passwordLabel.setFont(passwordLabelFont);
        passwordLabel.setForeground(new Color(-16382458));
        passwordLabel.setText("Password");
        panel2.add(passwordLabel, new com.intellij.uiDesigner.core.GridConstraints(10, 3, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(9, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(9, 15, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(1, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(5, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(2, 13, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(4, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(4, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer11 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer11, new com.intellij.uiDesigner.core.GridConstraints(4, 15, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer12 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer12, new com.intellij.uiDesigner.core.GridConstraints(4, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer13 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer13, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer14 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer14, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer15 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer15, new com.intellij.uiDesigner.core.GridConstraints(4, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer16 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer16, new com.intellij.uiDesigner.core.GridConstraints(4, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        logIn = new JLabel();
        Font logInFont = this.$$$getFont$$$("Cascadia Mono", Font.BOLD | Font.ITALIC, 14, logIn.getFont());
        if (logInFont != null) logIn.setFont(logInFont);
        logIn.setForeground(new Color(-16382458));
        logIn.setText("   Log in           ");
        panel2.add(logIn, new com.intellij.uiDesigner.core.GridConstraints(4, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer17 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer17, new com.intellij.uiDesigner.core.GridConstraints(6, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer18 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer18, new com.intellij.uiDesigner.core.GridConstraints(7, 11, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer19 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer19, new com.intellij.uiDesigner.core.GridConstraints(9, 12, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer20 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer20, new com.intellij.uiDesigner.core.GridConstraints(8, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        adminClientEmployee.setBackground(new Color(-2102558));
        panel1.add(adminClientEmployee, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logInButton = new JButton();
        logInButton.setBackground(new Color(-10040116));
        Font logInButtonFont = this.$$$getFont$$$("Cascadia Code", Font.BOLD, -1, logInButton.getFont());
        if (logInButtonFont != null) logInButton.setFont(logInButtonFont);
        logInButton.setText("Log in");
        panel1.add(logInButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registerButton = new JButton();
        registerButton.setBackground(new Color(-10040116));
        Font registerButtonFont = this.$$$getFont$$$("Cascadia Code", Font.BOLD, -1, registerButton.getFont());
        if (registerButtonFont != null) registerButton.setFont(registerButtonFont);
        registerButton.setText("Register");
        panel1.add(registerButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer21 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer21, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer22 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer22, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer23 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer23, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer24 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer24, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer25 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer25, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer26 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer26, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}