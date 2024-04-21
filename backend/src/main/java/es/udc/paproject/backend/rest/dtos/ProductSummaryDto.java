package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductSummaryDto {

    private Long id;
    private Long userId;
    private Long craftId;
    private Long subcategoryId;
    private String title;
    private BigDecimal price;
    private String productType;

    private String username;

    private String mainImageUrl;

    public ProductSummaryDto(){}

    public ProductSummaryDto(Long id, Long userId, Long craftId, Long subcategoryId, String title, BigDecimal price,
                             String productType, String username, String mainImageUrl){

        this.id=id;
        this.userId=userId;
        this.craftId= craftId;
        this.subcategoryId=subcategoryId;
        this.title=title;
        this.price= price;
        this.productType=productType;
        this.username=username;
        this.mainImageUrl= mainImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCraftId() {
        return craftId;
    }

    public void setCraftId(Long craftId) {
        this.craftId = craftId;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
