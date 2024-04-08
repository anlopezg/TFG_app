package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductDto {

    public interface AllValidations {}

    public interface UpdateValidations {}

    private Long id;
    private Long userId;
    private Long craftId;
    private Long subcategoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private Boolean active;

    public ProductDto(){}

    public ProductDto(Long id, Long user, Long craft, Long subcategory, String title, String description, BigDecimal price,
                      Boolean active){

        this.id=id;
        this.userId =user;
        this.craftId =craft;
        this.subcategoryId = subcategory;
        this.title=title;
        this.description=description;
        this.price=price;
        this.active=active;
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

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=60, groups={AllValidations.class})
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=500, groups={AllValidations.class})
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(groups = {AllValidations.class})
    @Digits(integer = 9, fraction = 2)
    @DecimalMin(value = "0.00")
    @DecimalMax(value="9999999.99")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(groups = {AllValidations.class})
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
