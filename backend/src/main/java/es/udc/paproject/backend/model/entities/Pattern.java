package es.udc.paproject.backend.model.entities;

import es.udc.paproject.backend.model.exceptions.MaxItemsExceededException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("PATTERN")
public class Pattern extends Product {

    public static final int MAX_ITEMS = 100;
    private String introduction;
    private String notes;
    private String gauge;
    private String sizing;
    private int difficultyLevel;
    private String time;
    private String abbreviations;
    private String specialAbbreviations;

    private String language;

    private Set<Tool> tools = new HashSet<>();
    private Set<Yarn> yarns = new HashSet<>();

    private Set<Section> sections = new HashSet<>();


    public Pattern(){
        super();
    }

    public Pattern(User user, Craft craft, Subcategory subcategory, String title, String description, BigDecimal price,
                   Boolean active, LocalDateTime creationDate,
                   String introduction, String notes, String gauge, String sizing, int difficultyLevel, String time,
                   String abbreviations, String specialAbbreviations, String language){

        super(user, craft, subcategory, title, description, price, active, creationDate);

        this.introduction=introduction;
        this.notes=notes;
        this.gauge=gauge;
        this.sizing=sizing;
        this.difficultyLevel=difficultyLevel;
        this.time=time;
        this.abbreviations = abbreviations;
        this.specialAbbreviations = specialAbbreviations;
        this.language = language;
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

    public String getAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(String abbreviations) {
        this.abbreviations = abbreviations;
    }

    public String getSpecialAbbreviations() {
        return specialAbbreviations;
    }

    public void setSpecialAbbreviations(String specialAbbreviations) {
        this.specialAbbreviations = specialAbbreviations;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @OneToMany(mappedBy = "pattern")
    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public void addTool(Tool tool) throws MaxItemsExceededException {
        if(tools.size() == MAX_ITEMS){
            throw new MaxItemsExceededException();
        }

        tools.add(tool);
        tool.setPattern(this);
    }

    @OneToMany(mappedBy = "pattern")
    public Set<Yarn> getYarns() {
        return yarns;
    }

    public void setYarns(Set<Yarn> yarns) {
        this.yarns = yarns;
    }

    public void addYarn(Yarn yarn) throws MaxItemsExceededException {
        if(yarns.size() == MAX_ITEMS){
            throw new MaxItemsExceededException();
        }

        yarns.add(yarn);
        yarn.setPattern(this);
    }

    @OneToMany(mappedBy = "pattern")
    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) throws MaxItemsExceededException {
        if(sections.size() == MAX_ITEMS){
            throw new MaxItemsExceededException();
        }

        sections.add(section);
        section.setPattern(this);
    }
}
