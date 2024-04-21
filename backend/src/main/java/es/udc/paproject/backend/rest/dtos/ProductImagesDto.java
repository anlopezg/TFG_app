package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.ProductImages;

public class ProductImagesDto {

    private String imageUrl;

    public ProductImagesDto(){}

    public ProductImagesDto(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
