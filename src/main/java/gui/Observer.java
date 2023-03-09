package gui;

import model.Order;

import java.util.ArrayList;

public interface Observer {
    void update(ArrayList<Order> orders);
}
