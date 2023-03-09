package dataaccess;

import businesslogic.MenuItem;
import model.Order;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class FileWriter {

    public void writeUsers(String path, HashSet<User> users) throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(path);

        for(User user:users){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(user.getUserID());
            stringBuilder.append(',');
            stringBuilder.append(user.getUserName());
            stringBuilder.append(',');
            stringBuilder.append(user.getPassword());
            stringBuilder.append('\n');

            List<String> fields = new ArrayList<>();
            fields.add(user.getUserName());
            fields.add(",");
            fields.add(user.getPassword());
            fields.add("\n");

            writer.println(stringBuilder);
        }

        writer.close();
    }

    /*public void writeProducts(String path, List<MenuItem> listOfMenuItem) throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(path);

        for(MenuItem menuItem: listOfMenuItem){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(menuItem.getNameProduct());
            stringBuilder.append(',');
            stringBuilder.append(menuItem.getRating());
            stringBuilder.append(',');
            stringBuilder.append(menuItem.getCalories());
            stringBuilder.append(',');

            stringBuilder.append(menuItem.getProteins());
            stringBuilder.append(',');
            stringBuilder.append(menuItem.getFats());
            stringBuilder.append(',');
            stringBuilder.append(menuItem.getSodium());
            stringBuilder.append(',');
            stringBuilder.append(menuItem.getPriceProduct());
            // stringBuilder.append('\n');

            List<String> fields = new ArrayList<>();
            fields.add(menuItem.getNameProduct());
            fields.add(",");
            fields.add(String.valueOf(menuItem.getRating()));
            fields.add(",");
            fields.add(String.valueOf(menuItem.getCalories()));
            fields.add(",");
            fields.add(String.valueOf(menuItem.getProteins()));
            fields.add(",");
            fields.add(String.valueOf(menuItem.getFats()));
            fields.add(",");
            fields.add(String.valueOf(menuItem.getSodium()));
            fields.add(",");
            fields.add(String.valueOf(menuItem.getPriceProduct()));
            fields.add("\n");

            writer.println(stringBuilder);
        }
        writer.close();
    }
*/

    public void writeMenuItems(String path, HashSet<MenuItem> menuItems) throws IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream((path)));
        objectOutputStream.writeObject(menuItems);
        objectOutputStream.close();
    }

    public static void report1Writer(List<Order> orders) {
        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter("report1.txt"))){
            for(Order order:orders)
            {
                writer.write("Order no: " + order.getOrderID() +
                        "\nClient: " + order.getClientName() +
                        "\nDate: " + order.getOrderDate() + "\n");
            }
            System.out.println("Report1 done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void report2(ArrayList<MenuItem> menuItems) {
        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter("report2.txt"))){
            for(MenuItem item:menuItems)
            {
                writer.write( item.getNameProduct() + " has been ordered " + item.getOrderedTimesNumber() + " times" + "\n");
            }
            System.out.println("Report2 done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void report3(List<User> users) {
        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter("report3.txt"))){
            for(User client:users)
            {
                writer.write(client.getUserName() + " has ordered " + client.getNumberOfOrders() + " times." + "\n");
            }
            System.out.println("Report3 done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void report4(List<Order> orders, int day) {
        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter("report4.txt"))){
            for(Order order:orders)
            {
                writer.write("The order " + order.getOrderID() + " was placed " + "on day " + day +"\n");
            }
            System.out.println("Report4 done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
