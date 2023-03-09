package model;

import businesslogic.IDeliveryServiceProcessing;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private int orderID;
    private String clientName;
    private LocalDateTime orderDate;
    private int price;

    public Order(int orderID, String clientName, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.clientName = clientName;
        this.orderDate = orderDate;
        this.price = 0;
    }

    public Order(){}

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getOrderHour() {
        return orderDate.getHour();
    }

    public int getOrderMounth() {
        return orderDate.getMonthValue();
    }

    public int getOrderDay() {
        return orderDate.getDayOfMonth();
    }


    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return this.orderID == order.orderID && clientName.equals(order.clientName) && orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientName,orderDate);
    }

}

