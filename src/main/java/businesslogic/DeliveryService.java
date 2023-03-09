package businesslogic;

import dataaccess.Serializatior;
import gui.AdminWindow;
import gui.Observer;
import model.Order;
import model.User;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;



/**
 * @invariant isWellFormed()
 * */

public class DeliveryService extends Serializatior implements IDeliveryServiceProcessing, Observable
{

    private ArrayList<Order> orders;
    private ArrayList<User> users;
    private ArrayList<MenuItem> menuItems;
    private ArrayList<MenuItem> shoppingProducts;
    private HashMap<Order, ArrayList<MenuItem>> orderHashMap;
    private ArrayList<Observer> observers = new ArrayList<>();
    private final Serializatior serializatior = new Serializatior();
    private int lastOrderIndex;


    public DeliveryService(ArrayList<Order> orders, ArrayList<User> users, ArrayList<MenuItem> menuItems, ArrayList<MenuItem> shoppingProducts,
                           HashMap<Order, ArrayList<MenuItem>> orderHashMap) {
        this.orders = orders;
        this.users = users;
        this.menuItems = menuItems;
        this.shoppingProducts = shoppingProducts;
        this.orderHashMap = orderHashMap;
        lastOrderIndex = 0;
    }

    public DeliveryService() {
    }

            /**SETTERS AND GETTERS */

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public ArrayList<MenuItem> getShoppingProducts() {
        return shoppingProducts;
    }

    public void setShoppingProducts(ArrayList<MenuItem> shoppingProducts) {
        this.shoppingProducts = shoppingProducts;
    }



    /**SETTERS AND GETTERS  --- END --- */


                /**ADMIN OPERATIONS*/
    public void modifyItems(MenuItem menuItem, int index){

        assert isWellFormed();
        assert menuItem != null;
        assert index > 0;
        MenuItem modifiedMenuItem = getMenuItems().get(index);
        menuItem.setNameProduct(menuItem.getNameProduct());
        menuItem.setRating(menuItem.computeRating());
        menuItem.setCalories(menuItem.computeCalories());
        menuItem.setProteins(menuItem.computeProteins());
        menuItem.setFats(menuItem.computeFats());
        menuItem.setSodium(menuItem.computeSodium());
        menuItem.setPriceProduct(menuItem.computePrice());

        menuItems.remove(index);
        menuItems.add(menuItem);

        assert menuItems.get(menuItems.size() - 1) == menuItem;
        assert isWellFormed();
    }

    public void addItem(MenuItem baseProduct, AdminWindow adminWindow){

        assert isWellFormed();
        assert baseProduct != null;

        ArrayList<MenuItem> menuItems = getMenuItems();
        int size = menuItems.size();
        menuItems.add(baseProduct);
        setMenuItems(menuItems);

//        adminWindow.getContentPane().remove(adminWindow.getScrollPane());
//        adminWindow.printTable(getMenuItems());

        assert menuItems.size() == size + 1;
        assert isWellFormed();

    }

    public void createCompositeProduct(String nameProduct, ArrayList<MenuItem> menuItemArrayList){

        assert isWellFormed();
        assert !nameProduct.equals("");
        assert menuItemArrayList != null;

        int size = menuItems.size();
        CompositeProduct compositeProduct = new CompositeProduct(menuItemArrayList, nameProduct);
        menuItems.add(compositeProduct);

        assert isWellFormed();
        assert size + 1 == menuItems.size();

    }

    public void deleteItem(int index){

        assert isWellFormed();
        assert index > 0;

        int size = menuItems.size();
        menuItems.remove(index);

        assert isWellFormed();
        assert menuItems.size() == size - 1;
    }

    public void report1(int startHour, int endHour){

        assert isWellFormed();
        assert startHour > 0 && endHour > 0;
        assert startHour < endHour;

        ArrayList<Order> reportOrders;

        reportOrders = (ArrayList<Order>) getOrders().stream()
                .filter(intervalStart -> (intervalStart.getOrderHour() >= startHour))
                .filter(intervalEnd -> (intervalEnd.getOrderHour() <= endHour))
                .collect(Collectors.toList());

        assert isWellFormed();
        dataaccess.FileWriter.report1Writer(reportOrders);

    }

    public void report2(int timesOrdered){

        assert isWellFormed();
        assert timesOrdered > 0;

        ArrayList<MenuItem> reportOrders;

        reportOrders = (ArrayList<MenuItem>) getMenuItems().stream()
                .filter(numberOfTimesOrdered -> (numberOfTimesOrdered.getOrderedTimesNumber() == timesOrdered))
                .collect(Collectors.toList());

        assert isWellFormed();
        dataaccess.FileWriter.report2(reportOrders);
    }

    public void report3(int minTimes, int minAmount){

        assert isWellFormed();
        assert minTimes > 0;
        assert minAmount > 0;

        ArrayList<User> clientsList = null;

        clientsList = (ArrayList<User>) getUsers().stream()
                .filter(client -> (client.getNumberOfOrders() > minTimes && client.getOrderPrices(minAmount)))
                .collect(Collectors.toList());

        assert isWellFormed();
        dataaccess.FileWriter.report3(clientsList);
    }

    public void report4(int day){

        assert isWellFormed();
        assert day > 0;
        ArrayList<Order> products;

        products = (ArrayList<Order>) orders.stream()
                .filter(product -> (product.getOrderDay() == day))
                .collect(Collectors.toList());

        assert isWellFormed();

        dataaccess.FileWriter.report4(products,day);
    }

    public int getLastOrderIndex() {
        return lastOrderIndex;
    }

    public void generateBill(String username, LocalDateTime localDateTime, int totalPrice){
        try {
            File orderBill = new File("Order" + getLastOrderIndex() + ".txt");
            if (orderBill.createNewFile()) {
                System.out.println("File created: " + orderBill.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            java.io.FileWriter myWriter = new FileWriter("Order" + getLastOrderIndex() + ".txt");
            myWriter.write("ID: " + getLastOrderIndex() +
                    "\nUsername: " + username + "\nDate: " + localDateTime.toLocalDate() +
                    "\nTime: "+ localDateTime.getHour() + ":" + localDateTime.getMinute() +
                    "\nTotal price: " + totalPrice);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public int computeTotalPrice(){
        int totalPrice = 0;
        for(MenuItem menuItem:shoppingProducts){
            totalPrice+=menuItem.computePrice();
        }
        return totalPrice;
    }

    public void addProductToShoppingProducts(MenuItem product){
        this.shoppingProducts.add(product);
    }

    public void emptyShoppingProducts(){
        this.shoppingProducts = new ArrayList<>();
    }

    public void createOrder(String username){

        String orderDetails = new String("");

        LocalDateTime localDateTime = LocalDateTime.now();
        
        int id = lastOrderIndex,totalPrice=0;

        Order order = new Order(id,username,localDateTime);
        orderHashMap.put(order,shoppingProducts);
        orders.add(order);

        totalPrice = computeTotalPrice();
        order.setPrice(totalPrice);

        generateBill(username,localDateTime,totalPrice);
        
        lastOrderIndex++;
        for(MenuItem menuItem: shoppingProducts){
            menuItem.incrementOrderedTimesNumber();
            for(MenuItem menuItem1 : menuItems){
                if(menuItem1.getNameProduct().equals(menuItem.getNameProduct())){
                    menuItem1.incrementOrderedTimesNumber();
                }
            }
        }

        for(User user:users){
            if(user.getUserName().equals(username)){
                user.incrementNumberOfOrders();
                user.addOrder(order);
            }
        }

        orderDetails = orderDetails + "ID: " + id + 
                "\nUsername: " + username +
                "\nDate: " + localDateTime.toLocalDate() + "\nTime: " + localDateTime.toLocalTime() +
                "\nTotal price: " + totalPrice + "\n\n\n";

        notifyAllObservers();
        //return orderDetails;
    }


                    /**CLIENT OPERATIONS*/

    public List<MenuItem> searchForProduct(String name, double rating, int calories, int protein, int fat, int sodium, int price){

        List<MenuItem> menuItemList;
        List<MenuItem> searchedList;
        menuItemList = getMenuItems();

        if(!name.equals(""))
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.getNameProduct().contains(name)))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(rating >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computeRating() == rating))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(calories >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computeCalories() == calories))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(protein >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computeProteins() == protein))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(fat >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computeFats() == fat))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(sodium >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computeSodium() == sodium))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }

        if(price >= 0)
        {
            searchedList = menuItemList.stream()
                    .filter(product -> (product.computePrice() == price))
                    .collect(Collectors.toList());

            menuItemList = searchedList;
        }
        return menuItemList;

    }

//    public void placeOrder(int index){
//
//        CreateOrdersWindow createOrdersWindow = new CreateOrdersWindow("Place an order");
//        MenuItem menuItem = menuItems.get(index);
//        menuItem.incrementOrderedTimesNumber();
//        shoppingProducts.add(menuItem);
//
//        createOrdersWindow.getContentPane().remove(createOrdersWindow.getScrollPane());
//        createOrdersWindow.printTable(shoppingProducts);
//
//    }



    public boolean isWellFormed(){
        if(orders == null)
            return false;
        if(users == null)
            return false;
        if(menuItems == null)
            return false;
        if(shoppingProducts == null)
            return false;
        if(orderHashMap == null)
            return false;
        if(lastOrderIndex < 0)
            return false;
        return true;
   }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer:observers){
            observer.update(orders);
        }

    }
}
