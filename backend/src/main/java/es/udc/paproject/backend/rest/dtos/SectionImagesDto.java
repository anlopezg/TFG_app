package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.SectionImages;

public class SectionImagesDto {

    private String imageUrl;

    public SectionImagesDto() {}

    public SectionImagesDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static SectionImagesDto toSectionImagesDto(SectionImages sectionImages){
        return new SectionImagesDto(sectionImages.getImageUrl());
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
