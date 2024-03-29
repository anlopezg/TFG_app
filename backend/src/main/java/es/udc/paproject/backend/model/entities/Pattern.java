package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PATTERN")
public class Pattern extends Product{
    private String introduction;
    private String notes;
    private String gauge;
    private String sizing;
    private int difficultyLevel;
    private String time;

    public Pattern(){
        super();
    }

    public Pattern(User user, Craft craft, Subcategory subcategory, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate,
                   String introduction, String notes, String gauge, String sizing, int difficultyLevel, String time){

        super(user, craft, subcategory, title, description, price, active, creationDate);

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
