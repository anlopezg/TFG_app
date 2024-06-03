package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.*;

@Entity
public class PurchaseItem {

    private Long id;
    private Product product;
    private Purchase purchase;
    private BigDecimal productPrice;
    private int quantity;
    private Payment payment;


    public PurchaseItem() {}

    public PurchaseItem(Product product, BigDecimal productPrice, int quantity) {

        this.product = product;
        this.productPrice = productPrice.setScale(2, RoundingMode.HALF_EVEN);
        this.quantity = quantity;

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

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="purchaseId")
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Transient
    public BigDecimal getTotalPrice() {
        return productPrice.multiply(new BigDecimal(quantity));
    }

    @OneToOne(mappedBy="purchaseItem", optional=false, fetch=FetchType.LAZY)
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
