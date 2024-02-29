package es.udc.paproject.backend.rest.dtos;

import java.util.Set;

public class CategoryDto {

    private Long id;
    private String categoryName;
    private Set<SubcategoryDto> subcategories;

    public CategoryDto() {}

    public CategoryDto(Long id, String categoryName, Set<SubcategoryDto> subcategories) {
        this.id = id;
        this.categoryName = categoryName;
        this.subcategories = subcategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }
}
