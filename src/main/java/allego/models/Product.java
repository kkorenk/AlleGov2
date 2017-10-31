package allego.models;

import allego.models.cart.ProductToCartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ibm on 2017-10-16.
 */
@Entity
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int quantity;

    @Transient
    private MultipartFile image;


    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductToCartItem> productToCartItemList;

    public Product() {}
    public Product(String name, BigDecimal price, String description, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public List<ProductToCartItem> getProductToCartItemList() {
        return productToCartItemList;
    }

    public void setProductToCartItemList(List<ProductToCartItem> productToCartItemList) {
        this.productToCartItemList = productToCartItemList;
    }

}
