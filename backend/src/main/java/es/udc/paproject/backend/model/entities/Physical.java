package es.udc.paproject.backend.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PHYSICAL")
public class Physical extends Product {

    private int amount;
    private String size;
    private String color;
    private String details;

    public Physical(){
        super();
    }

    public Physical(User user, Craft craft, Subcategory subcategory, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate,
                   int amount, String size, String color, String details){

        super(user, craft, subcategory, title, description, price, active, creationDate);

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
