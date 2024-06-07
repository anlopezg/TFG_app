package es.udc.paproject.backend.rest.dtos;


import es.udc.paproject.backend.model.entities.Section;
import es.udc.paproject.backend.model.entities.Step;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

import static es.udc.paproject.backend.rest.dtos.SectionImagesDto.toSectionImagesDto;
import static es.udc.paproject.backend.rest.dtos.StepDto.toStepDto;

public class SectionDto {

    private String title;
    private String description;

    private int sectionOrder;
    private List<StepDto> steps;
    private List<SectionImagesDto> imagesUrl;


    public SectionDto() {}

    public SectionDto(String title, String description, int sectionOrder, List<StepDto> steps, List<SectionImagesDto> imagesUrl) {

        this.title = title;
        this.description = description;
        this.sectionOrder = sectionOrder;
        this.steps = steps;
        this.imagesUrl = imagesUrl;
    }

    /**
     * Conversor from Section to SectionDto
     * @param section Section
     * @return SectionDto
     */
    public static SectionDto toSectionDto (Section section){

        List<StepDto> steps = section.getSteps().stream().map(i -> toStepDto(i)).collect(Collectors.toList());

        List<SectionImagesDto> images = section.getImages().stream().map(i -> toSectionImagesDto(i)).collect(Collectors.toList());

        return new SectionDto(section.getTitle(), section.getDescription(), section.getSectionOrder(), steps, images);
    }

    public static List<Section> toSections(List<SectionDto> sectionDtos) {

        return sectionDtos.stream().map(dto -> {
            Section section = new Section();
            section.setTitle(dto.getTitle());
            section.setDescription(dto.getDescription());
            section.setSteps(dto.getSteps().stream()
                    .map(stepDto -> new Step(stepDto.getRowNumber(), stepDto.getInstructions(), stepDto.getStepOrder()))
                    .collect(Collectors.toSet()));
            return section;
        }).collect(Collectors.toList());
    }


    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min = 1, max = 32, groups={ProductDto.AllValidations.class})
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min = 1, max = 400, groups={ProductDto.AllValidations.class})
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min = 1, max = 100)
    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min = 1, max = 100)
    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps;
    }


    @NotNull(groups = {ProductDto.AllValidations.class})
    @Size(min = 1, max = 5)
    public List<SectionImagesDto> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<SectionImagesDto> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
