package model;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    private int userID;
    private String userName;
    private String password;
    private int numberOfOrders;
    private ArrayList<Order> orders;

    public User(String userName, String password, int userID){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.numberOfOrders = 0;
        this.orders = new ArrayList<>();
    }

    public User() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void incrementNumberOfOrders() {
        this.numberOfOrders++;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public boolean getOrderPrices(int minPrice){

        for(Order order: orders){
            if(order.getPrice() > minPrice)
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
