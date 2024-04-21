package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public class PhysicalDto extends ProductDto{

    private int amount;
    private String size;
    private String color;
    private String details;
    private List<String> imagesUrl;


    private PhysicalDto(){}

    public PhysicalDto(Long id, Long user, Long craft, Long subcategory, String title, String description, BigDecimal price,
                       Boolean active,int amount, String size, String color, String details,
                       List<String> imagesUrl){

        super(id, user, craft, subcategory, title, description, price, active);

        this.amount=amount;
        this.size=size;
        this.color=color;
        this.details=details;
        this.imagesUrl=imagesUrl;
    }

    @NotNull(groups = {AllValidations.class})
    @Min(value=0)
    @Max(value=10000)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=60, groups={AllValidations.class})
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=60, groups={AllValidations.class})
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Size(min=1, max=500, groups={AllValidations.class})
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @NotNull(groups = {AllValidations.class})
    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
