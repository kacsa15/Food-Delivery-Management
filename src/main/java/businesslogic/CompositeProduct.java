package businesslogic;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem{


    private List<MenuItem> compositeProductsList;

    public CompositeProduct(ArrayList<MenuItem> compositeProducts, String nameProduct) {
        super(nameProduct);
        this.compositeProductsList = compositeProducts;
        this.price = computePrice();
        this.rating = computeRating();
        this.calories = computeCalories();
        this.protein = computeProteins();
        this.fat = computeFats();
        this.sodium = computeSodium();
    }


    @Override
    public int computePrice() {
        int price = 0;
        for(MenuItem menuItem: compositeProductsList){
            price += menuItem.computePrice();
        }
        return price;
    }

    @Override
    public double computeRating() {
        double rating = 0;
        for(MenuItem menuItem: compositeProductsList){
            rating += menuItem.computeRating();
        }
        return rating/compositeProductsList.size();
    }

    @Override
    public int computeCalories() {
        int calories = 0;
        for(MenuItem menuItem: compositeProductsList){
            calories += menuItem.computeCalories();
        }
        return calories;
    }

    @Override
    public int computeProteins() {
        int proteins = 0;
        for(MenuItem menuItem: compositeProductsList){
            proteins += menuItem.computeProteins();
        }
        return proteins;
    }

    @Override
    public int computeFats() {
        int fat = 0;
        for(MenuItem menuItem: compositeProductsList){
            fat += menuItem.computeFats();
        }
        if(fat == 0) System.out.println(":(((");
        return fat;
    }

    @Override
    public int computeSodium() {
        int sodium = 0;
        for(MenuItem menuItem: compositeProductsList){
            sodium += menuItem.computeSodium();
        }
        return sodium;
    }


                                                ////SETTERS////

    @Override
    public void setRating(double rating) {

    }

    @Override
    public void setCalories(int calories) {

    }

    @Override
    public void setProteins(int proteins) {

    }

    @Override
    public void setFats(int fats) {

    }

    @Override
    public void setSodium(int sodium) {

    }

    @Override
    public void setPriceProduct(int priceProduct) {

    }


    public List<MenuItem> getCompositeProductsList() {
        return compositeProductsList;
    }

    public void setCompositeProductsList(List<MenuItem> compositeProductsList) {
        this.compositeProductsList = compositeProductsList;
    }
}
