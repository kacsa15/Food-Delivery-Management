package dataaccess;

import businesslogic.BaseProduct;
import businesslogic.MenuItem;
import model.User;

import java.io.*;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReadFile {

    public HashSet<MenuItem> readProducts(String path) throws IOException {
        HashSet<MenuItem> listOfProducts;

        File inputFile = new File(path);
        InputStream inputStream = new FileInputStream(inputFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        listOfProducts = (HashSet<MenuItem>) bufferedReader.lines().skip(1).map(readMenuItem).collect(Collectors.toSet());
        bufferedReader.close();

        return listOfProducts;
    }

    public HashSet<User> readUsers(String path) throws IOException {
        HashSet<User> listOfUsers;

        File inputFile = new File(path);
        InputStream inputStream = new FileInputStream(inputFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        listOfUsers = (HashSet<User>) bufferedReader.lines().skip(1).map(buildUser).collect(Collectors.toSet());
        bufferedReader.close();

        return listOfUsers;
    }

    private Function<String, MenuItem> readMenuItem = line -> {

        MenuItem menuItem = new BaseProduct();
        String[] data = line.split(",");

        // product name
        menuItem.setNameProduct(data[0]);

        if(data.length > 6){
            //rating
            if (data[1] != null && data[1].trim().length() > 0) {
                menuItem.setRating(Double.parseDouble(data[1]));
            }

            //calories
            if (data[2] != null && data[2].trim().length() > 0) {
                menuItem.setCalories(Integer.parseInt(data[2]));
            }

            //protein
            if (data[3] != null && data[3].trim().length() > 0) {
                menuItem.setProteins(Integer.parseInt(data[3]));
            }

            //fat
            if (data[4] != null && data[4].trim().length() > 0) {
                menuItem.setFats(Integer.parseInt(data[4]));
            }

            //sodium
            if (data[5] != null && data[5].trim().length() > 0) {
                menuItem.setSodium(Integer.parseInt(data[5]));
            }

            //price
            if (data[6] != null && data[6].trim().length() > 0) {
                menuItem.setPriceProduct(Integer.parseInt(data[6]));
            }
        }

        return menuItem;
    };

    private Function<String, User> buildUser = line -> {
        User user = new User();
        String[] infoUser = line.split(",");

        //System.out.println(infoUser[0] + " " + infoUser[1]);
        if(!infoUser[0].equals("")){
            user.setUserID(Integer.parseInt(infoUser[0]));
        }
        if(infoUser.length > 2){
            user.setUserName(infoUser[1]);
            user.setPassword(infoUser[2]);}
        return user;
    };

}
