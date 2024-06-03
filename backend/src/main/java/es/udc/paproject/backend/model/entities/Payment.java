package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {

    private Long id;

    private PurchaseItem purchaseItem;
    private String paymentId;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime paymentDate;
    private String stripeAccountId;
    private String stripeTransactionId;


    public Payment() {}

    public Payment(PurchaseItem purchaseItem, String paymentId, String paymentMethod, String paymentStatus, BigDecimal amount, String currency, LocalDateTime paymentDate, String stripeAccountId, String stripeTransactionId) {
        this.purchaseItem = purchaseItem;
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.currency = currency;
        this.paymentDate = paymentDate;
        this.stripeAccountId = stripeAccountId;
        this.stripeTransactionId = stripeTransactionId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name= "purchaseItemId")
    public PurchaseItem getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStripeAccountId() {
        return stripeAccountId;
    }

    public void setStripeAccountId(String stripeAccountId) {
        this.stripeAccountId = stripeAccountId;
    }

    public String getStripeTransactionId() {
        return stripeTransactionId;
    }

    public void setStripeTransactionId(String stripeTransactionId) {
        this.stripeTransactionId = stripeTransactionId;
    }
}
