package es.udc.paproject.backend.rest.dtos;

public class SubcategoryDto {

    private Long id;
    private String subcategoryName;
    private Long parentCategoryId;

    public SubcategoryDto() {}

    public SubcategoryDto(Long id, String subcategoryName, Long parentCategoryId) {
        this.id = id;
        this.subcategoryName = subcategoryName;
        this.parentCategoryId = parentCategoryId;
    }

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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
