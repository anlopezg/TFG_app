package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {

    private Long id;
    private User user;
    private Product product;
    private int rating;
    private String comment;
    private LocalDateTime date;

    public Review(){}

    public Review(int rating, String comment, LocalDateTime date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Review(User user, Product product, int rating, String comment, LocalDateTime date){

        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
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
    @JoinColumn(name= "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name= "productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime datePublished) {
        this.date = datePublished;
    }
}
