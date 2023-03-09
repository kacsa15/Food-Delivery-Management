package businesslogic;


import java.util.Objects;

public abstract class MenuItem{

    private String nameProduct;
    private int orderedTimesNumber;
    protected double rating;
    protected int calories;
    protected int protein;
    protected int fat;
    protected int sodium;
    protected int price;


    public MenuItem(String name){
        this.nameProduct = name;
        this.orderedTimesNumber = 0;
    }
    public MenuItem(){
    }


    public abstract int computePrice();
    public abstract double computeRating();
    public abstract int computeCalories();
    public abstract int computeProteins();
    public abstract int computeFats();
    public abstract int computeSodium();


    public abstract void setRating(double rating);
    public abstract void setCalories(int calories);
    public abstract void setProteins(int proteins);
    public abstract void setFats(int fats);
    public abstract void setSodium(int sodium);
    public abstract void setPriceProduct(int priceProduct);

    public void setNameProduct(String nameProduct){
        this.nameProduct = nameProduct;
    }
    
    public String getNameProduct(){
        return nameProduct;
    }

    public int getOrderedTimesNumber() {
        return orderedTimesNumber;
    }

    public void incrementOrderedTimesNumber() {
        this.orderedTimesNumber++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return nameProduct.equals(menuItem.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameProduct);
    }

}
