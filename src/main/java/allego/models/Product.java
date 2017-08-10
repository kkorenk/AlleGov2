package allego.models;

/**
 * Created by ibm on 2017-07-14.
 */
public class Product {

    private int id;
    private String category;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product() {}

    public Product(int id, String category, String name, String description, double price, int quantity) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
