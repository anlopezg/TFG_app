package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

@Entity
public class Yarn {

    private Long id;
    private Pattern pattern;
    private String brand;
    private String name;
    private String color;
    private String amount;
    private String fiberContent;
    private String weight;
    private String length;

    public Yarn() {}

    public Yarn(String brand, String name, String color, String amount, String fiberContent, String weight, String length) {
        this.brand = brand;
        this.name = name;
        this.color = color;
        this.amount = amount;
        this.fiberContent = fiberContent;
        this.weight = weight;
        this.length = length;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional=false, fetch= FetchType.LAZY)
    @JoinColumn(name= "productId")
    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFiberContent() {
        return fiberContent;
    }

    public void setFiberContent(String fiberContent) {
        this.fiberContent = fiberContent;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
