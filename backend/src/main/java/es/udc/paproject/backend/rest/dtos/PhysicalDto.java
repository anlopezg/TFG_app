package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class PhysicalDto extends ProductDto{

    private int amount;
    private String size;
    private String color;
    private String details;


    private PhysicalDto(){}

    public PhysicalDto(Long id, Long user, Long craft, Long subcategory, String title, String description, BigDecimal price,
                       Boolean active,int amount, String size, String color, String details){

        super(id, user, craft, subcategory, title, description, price, active);

        this.amount=amount;
        this.size=size;
        this.color=color;
        this.details=details;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
