package es.udc.paproject.backend.rest.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;

public class PatternDto extends ProductDto{

    private String introduction;
    private String notes;
    private String gauge;
    private String sizing;
    private int difficultyLevel;
    private String time;
    private String abbreviations;
    private String specialAbbreviations;
    private String tools;

    private List<String> imagesUrl;


    private PatternDto(){}

    public PatternDto(Long id, Long user, Long craft, Long subcategory, String title, String description, BigDecimal price,
                      Boolean active,String introduction, String notes, String gauge, String sizing,
                      int difficultyLevel, String time, String abbreviations, String specialAbbreviations, String tools,
                      List<String> imagesUrl){

        super(id, user, craft, subcategory, title, description, price, active);

        this.introduction=introduction;
        this.notes=notes;
        this.gauge=gauge;
        this.sizing=sizing;
        this.difficultyLevel=difficultyLevel;
        this.time=time;
        this.abbreviations = abbreviations;
        this.specialAbbreviations = specialAbbreviations;
        this.tools= tools;
        this.imagesUrl = imagesUrl;

    }


    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=500, groups={AllValidations.class})
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Nullable
    @Size(min=1, max=500, groups={AllValidations.class})
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=200, groups={AllValidations.class})
    public String getGauge() {
        return gauge;
    }

    public void setGauge(String gauge) {
        this.gauge = gauge;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=200, groups={AllValidations.class})
    public String getSizing() {
        return sizing;
    }

    public void setSizing(String sizing) {
        this.sizing = sizing;
    }

    @NotNull(groups = {AllValidations.class})
    @Min(value=0)
    @Max(value=3)
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=60, groups={AllValidations.class})
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(String abbreviations) {
        this.abbreviations = abbreviations;
    }

    @Nullable
    @Size(min=1, max=500, groups={AllValidations.class})
    public String getSpecialAbbreviations() {
        return specialAbbreviations;
    }

    public void setSpecialAbbreviations(String specialAbbreviations) {
        this.specialAbbreviations = specialAbbreviations;
    }

    @NotNull(groups = {AllValidations.class})
    @Size(min=1, max=500, groups={AllValidations.class})
    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }


    @NotNull(groups = {AllValidations.class})
    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
