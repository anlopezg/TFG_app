package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    private Long id;
    private String categoryName;
    private Set<Subcategory> subcategories = new HashSet<>();

    public Category(){}

    public Category(String categoryName, Set<Subcategory> subcategories) {
        this.categoryName = categoryName;
        this.subcategories=subcategories;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "category")
    public Set<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
