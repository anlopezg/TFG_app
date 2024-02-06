package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedTransferQueue;

@Entity
public class Pattern extends Publication{

    public enum Level {BEGINNER, INTERMEDIATE, ADVANCED}

    private String introduction;
    private String notes;
    private String gauge;
    private String size;
    private Level difficultyLevel;
    private String time;

    public Pattern(){
        super();
    }

    public Pattern(User user, Craft craft, Category category, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate,
                   String introduction, String notes, String gauge, String size, Level difficultyLevel, String time){

        super(user, craft, category, title, description, price, active, creationDate);

        this.introduction=introduction;
        this.notes=notes;
        this.gauge=gauge;
        this.size=size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Level getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Level difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
