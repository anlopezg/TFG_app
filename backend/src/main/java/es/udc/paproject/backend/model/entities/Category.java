package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    private Long id;
    private Category parent;
    private String categoryName;


    private Set<Category> subcategories = new HashSet<>();

    public Category(){}

    public Category(Category parent, String categoryName){
        this.parent = parent;
        this.categoryName = categoryName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="parentId")
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
