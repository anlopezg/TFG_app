package es.udc.paproject.backend.model.entities;

import es.udc.paproject.backend.model.entities.Category;
import jakarta.persistence.*;

@Entity
public class Subcategory {

    private Long id;
    private String subcategoryName;

    private Category category;

    public Subcategory(){}

    public Subcategory(String subcategoryName, Category parentCategory){
        this.subcategoryName=subcategoryName;
        this.category=parentCategory;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
