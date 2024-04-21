package es.udc.paproject.backend.model.entities;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "productType")
public class Product{
    private Long id;
    private User user;
    private Craft craft;
    private Subcategory subcategory;
    private String title;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private LocalDateTime creationDate;

    private Set<ProductImages> images = new HashSet<>();


    @Version
    private String productType;

    public Product(){}

    public Product(User user, Craft craft, Subcategory subcategory, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate){

        this.user=user;
        this.craft=craft;
        this.subcategory = subcategory;
        this.title=title;
        this.description=description;
        this.price=price;
        this.active=active;
        this.creationDate=creationDate;
    }

    public Product(User user, Craft craft, Subcategory subcategory, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate, String productType){

        this.user=user;
        this.craft=craft;
        this.subcategory = subcategory;
        this.title=title;
        this.description=description;
        this.price=price;
        this.active=active;
        this.creationDate=creationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="craftId")
    public Craft getCraft() {
        return craft;
    }

    public void setCraft(Craft craft) {
        this.craft = craft;
    }


    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="subcategoryId")
    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    @OneToMany(mappedBy="product")
    public Set<ProductImages> getImages() {
        return images;
    }

    public void setImages(Set<ProductImages> images) {
        this.images = images;
    }

    public void addImage(ProductImages image){
        images.add(image);
        image.setProduct(this);
    }

    @Transient
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
