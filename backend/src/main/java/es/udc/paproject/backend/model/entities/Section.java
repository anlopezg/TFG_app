package es.udc.paproject.backend.model.entities;

import es.udc.paproject.backend.model.exceptions.MaxItemsExceededException;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Section {

    public static final int MAX_STEPS = 100;
    public static final int MAX_IMAGES = 5;
    private Long id;
    private Pattern pattern;
    private String title;
    private String description;
    private int sectionOrder;

    private Set<Step> steps = new HashSet<>();

    private Set<SectionImages> images = new HashSet<>();


    public Section() {}

    public Section(Pattern pattern, String title, String description) {
        this.pattern = pattern;
        this.title = title;
        this.description = description;
    }

    public Section(String title, String description, int sectionOrder) {
        this.title = title;
        this.description = description;
        this.sectionOrder = sectionOrder;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    @OneToMany(mappedBy = "section")
    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) throws MaxItemsExceededException{
        if(steps.size() == MAX_STEPS){
            throw new MaxItemsExceededException();
        }

        steps.add(step);
        step.setSection(this);
    }

    @OneToMany(mappedBy="section")
    public Set<SectionImages> getImages() {
        return images;
    }

    public void setImages(Set<SectionImages> images) {
        this.images = images;
    }

    public void addSectionImage(SectionImages sectionImage) throws MaxItemsExceededException{
        if(images.size() == MAX_IMAGES){
            throw new MaxItemsExceededException();
        }

        images.add(sectionImage);
        sectionImage.setSection(this);
    }
}
