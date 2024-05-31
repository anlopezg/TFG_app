package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Section {

    private Long id;
    private Pattern pattern;
    private String title;
    private String description;

    private Set<Step> steps = new HashSet<>();

    private Set<SectionImages> images = new HashSet<>();


    public Section() {}

    public Section(Pattern pattern, String title, String description) {
        this.pattern = pattern;
        this.title = title;
        this.description = description;
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

    @OneToMany(mappedBy = "section")
    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    @OneToMany(mappedBy="section")
    public Set<SectionImages> getImages() {
        return images;
    }

    public void setImages(Set<SectionImages> images) {
        this.images = images;
    }
}
