package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class PurchaseItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;
    private String mainImageUrl;
    private String paymentStatus;

    public PurchaseItemDto() {}

    public PurchaseItemDto(Long id, Long productId, String productName, BigDecimal productPrice, int quantity,
                           String mainImageUrl, String paymentStatus) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.mainImageUrl = mainImageUrl;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
