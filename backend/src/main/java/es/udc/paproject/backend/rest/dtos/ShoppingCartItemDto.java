package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ShoppingCartItemDto {

    private Long productId;
    private String productName;
    private Long subcategoryId;
    private BigDecimal productPrice;
    private int quantity;
    private String mainImageUrl;

    private String productType;

    // Add physicals maxAmount
    private int maxAmount;



    public ShoppingCartItemDto() {}

    public ShoppingCartItemDto(Long productId, String productName, Long subcategoryId, BigDecimal productPrice,
                               int quantity, String mainImageUrl, String productType) {

        this.productId = productId;
        this.productName = productName;
        this.subcategoryId = subcategoryId;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.mainImageUrl = mainImageUrl;
        this.productType = productType;

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

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
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

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
