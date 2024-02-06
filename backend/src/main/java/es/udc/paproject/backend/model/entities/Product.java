package es.udc.paproject.backend.model.entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public abstract class Product extends Publication{

    private int amount;
    private String size;
    private String color;


    public Product(){
        super();
    }

    public Product(User user, Craft craft, Category category, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate,
                   int amount, String size, String color){

        super(user, craft, category, title, description, price, active, creationDate);

        this.amount=amount;
        this.size=size;
        this.color=color;
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

}
