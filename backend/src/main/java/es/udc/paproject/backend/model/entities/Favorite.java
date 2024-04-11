package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

@Entity
public class Favorite {

    private Long id;

    private User user;
    private Product product;
    private Boolean isFavorite;

    public Favorite(){}

    public Favorite(User user, Product product, Boolean isFavorite){
        this.user=user;
        this.product=product;
        this.isFavorite=isFavorite;
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
    @JoinColumn(name="productId")
    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
