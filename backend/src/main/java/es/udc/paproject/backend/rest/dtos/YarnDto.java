package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Yarn;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class YarnDto {

    private String brand;
    private String name;
    private String color;
    private String amount;
    private String fiberContent;
    private String weight;
    private String length;

    public YarnDto() {}

    public YarnDto(String brand, String name, String color, String amount, String fiberContent, String weight, String length) {

        this.brand = brand.trim();
        this.name = name.trim();
        this.color = color.trim();
        this.amount = amount.trim();
        this.fiberContent = fiberContent.trim();
        this.weight = weight.trim();
        this.length = length.trim();
    }


    /**
     * Conversor from Yarn to YarnDto
     * @param yarn Yarn
     * @return YarnDto
     */
    public static YarnDto toYarnDto(Yarn yarn){
        return new YarnDto(yarn.getBrand(), yarn.getName(), yarn.getColor(), yarn.getAmount(), yarn.getFiberContent(),
                yarn.getWeight(), yarn.getLength());
    }

    /**
     * Conversor from a List of YarnDto to List of Yarn
     * @param yarnDtos List of YarnDto
     * @return List of Yarn
     */
    public static List<Yarn> toYarns(List<YarnDto> yarnDtos) {
        return yarnDtos.stream()
                .map(dto -> new Yarn(dto.getBrand(), dto.getName(), dto.getColor(), dto.getAmount(), dto.getFiberContent(), dto.getWeight(), dto.getLength()))
                .collect(Collectors.toList());
    }


    @NotNull
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NotNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @NotNull
    public String getFiberContent() {
        return fiberContent;
    }

    public void setFiberContent(String fiberContent) {
        this.fiberContent = fiberContent;
    }

    @NotNull
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @NotNull
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
