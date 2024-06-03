package es.udc.paproject.backend.model.entities;

import es.udc.paproject.backend.model.exceptions.MaxItemsExceededException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Purchase {

    public static final int MAX_ITEMS = 20;

    private Long id;
    private Set<PurchaseItem> items = new HashSet<>();
    private User user;
    private LocalDateTime date;
    private String postalAddress;
    private String locality;
    private String region;
    private String country;
    private String postalCode;

    public Purchase() {}

    public Purchase(User user, LocalDateTime date, String postalAddress, String locality,
                    String region, String country, String postalCode) {

        this.user = user;
        this.date = date;
        this.postalAddress = postalAddress;
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy="purchase")
    public Set<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(Set<PurchaseItem> items) {
        this.items = items;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name= "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Transient
    public Optional<PurchaseItem> getItem(Long productId) {
        return items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
    }

    public void addItem(PurchaseItem item) throws MaxItemsExceededException {

        if (items.size() == MAX_ITEMS) {
            throw new MaxItemsExceededException();
        }

        items.add(item);
        item.setPurchase(this);

    }

    @Transient
    public BigDecimal getTotalPrice() {
        return items.stream().map(i -> i.getTotalPrice()).reduce(new BigDecimal(0), (a, b) -> a.add(b));
    }
}