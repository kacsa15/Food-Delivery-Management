package businesslogic;

import gui.AdminWindow;
import model.Order;
import model.User;

import java.util.ArrayList;


public interface IDeliveryServiceProcessing {

    /**
     * @pre menuItem!=null
     * @pre index > 0
     * @post menuItems.get(menuItems.size - 1) == menuItem
     */
    void modifyItems(MenuItem menuItem, int index);

    /**
     * @pre baseProduct!=null
     * @post menuItems.size() == size@pre + 1;
     */
    void addItem(MenuItem baseProduct, AdminWindow adminWindow);

    /**
     * @pre !nameProduct.equals("")
     * @pre menuItemArrayList != null
     * @post menuItems.getSize() == menuItems.getSize()@pre + 1
     */
    void createCompositeProduct(String nameProduct, ArrayList<MenuItem> menuItemArrayList);

    /**
     * @pre index <0
     * @post getSize() == getSize()@pre - 1
     */
    void deleteItem(int index);

    /**
     * @pre startHour <endHour
     * @pre startHour >0&&endHour >0
     * @post @nochange
     */
    void report1(int startHour, int endHour);

    /**
     * @pre timesOrdered >0
     * @post @nochange
     */
    void report2(int timesOrdered);

    /**
     * @pre minTimes >0
     * @pre minAmount >0
     * @post @nochange
     */
    void report3(int minTimes, int minAmount);

    /**
     * @pre day >0
     * @post @nochange
     */
    void report4(int day);

}
