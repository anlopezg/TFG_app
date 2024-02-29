package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class PatternDto extends ProductDto{

    private String introduction;
    private String notes;
    private String gauge;
    private String sizing;
    private int difficultyLevel;
    private String time;

    private PatternDto(){}

    public PatternDto(Long id, Long user, Long craft, Long subcategory, String title, String description, BigDecimal price,
                      Boolean active,String introduction, String notes, String gauge, String sizing,
                      int difficultyLevel, String time){

        super(id, user, craft, subcategory, title, description, price, active);

        this.introduction=introduction;
        this.notes=notes;
        this.gauge=gauge;
        this.sizing=sizing;
        this.difficultyLevel=difficultyLevel;
        this.time=time;

    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGauge() {
        return gauge;
    }

    public void setGauge(String gauge) {
        this.gauge = gauge;
    }

    public String getSizing() {
        return sizing;
    }

    public void setSizing(String sizing) {
        this.sizing = sizing;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
