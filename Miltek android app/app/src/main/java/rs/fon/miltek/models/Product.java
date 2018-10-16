package rs.fon.miltek.models;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Product implements Serializable{
    private int productID;
    private String title;
    private double price;
    private int brandID;
    private int categoryID;
    private String image;
    private String description;
    private int featured;
    private int quantity;
    DecimalFormat df = new DecimalFormat("#0.00");

    public Product(int productID, String title, double price, int brandID, int categoryID, String image, String description, int featured, int quantity) {
        this.productID = productID;
        this.title = title;
        this.price = price;
        this.brandID = brandID;
        this.categoryID = categoryID;
        this.image = image;
        this.description = description;
        this.featured = featured;
        this.quantity = quantity;
    }

    public Product(String title, String image, String description, int quantity){
        this.title = title;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceS() {
        return df.format(price)+" RSD";
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getImage() {
        if(image.isEmpty()){
            return "";
        }
        return String.format("https://miltek.000webhostapp.com/img/products/%s", image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
