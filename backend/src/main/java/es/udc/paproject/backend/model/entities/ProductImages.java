package es.udc.paproject.backend.model.entities;


import jakarta.persistence.*;

@Entity
public class ProductImages {

    private Long id;
    private Product product;
    private String imageUrl;

    public ProductImages(){}

    public ProductImages(Product product, String imageUrl){
        this.product=product;
        this.imageUrl = imageUrl;
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
    @JoinColumn(name="productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
