package businesslogic;

public class BaseProduct extends MenuItem{

    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public BaseProduct(String nameProduct, int price, double rating, int fat,int protein,int sodium, int calories){

        super(nameProduct);

        this.price = price;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
    }

    public BaseProduct(){}

    @Override
    public int computePrice() {
        return price;
    }

    @Override
    public double computeRating() {
        return rating;
    }

    @Override
    public int computeCalories() {
        return calories;
    }

    @Override
    public int computeProteins() {
        return protein;
    }

    @Override
    public int computeFats() {
        return this.fat;
    }

    @Override
    public int computeSodium() {
        return sodium;
    }

    
                                                                    ////SETTERS////
    
    @Override
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public void setProteins(int proteins) {
        this.protein = proteins;
    }

    @Override
    public void setFats(int fats) {
        this.fat = fats;
    }

    @Override
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
    
    @Override
    public void setPriceProduct(int priceProduct) {
        this.price = priceProduct;
    }





}
