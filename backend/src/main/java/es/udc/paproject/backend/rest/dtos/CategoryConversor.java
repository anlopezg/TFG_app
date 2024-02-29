package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Subcategory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryConversor {

    private CategoryConversor(){}


    /* CONVERSOR FOR CATEGORIES */
    public final static CategoryDto toCategoryDto(Category category) {
        Set<SubcategoryDto> subcategoryDtos = new HashSet<>();
        for (Subcategory subcategory : category.getSubcategories()) {
            subcategoryDtos.add(toSubcategoryDto(subcategory));
        }
        return new CategoryDto(category.getId(), category.getCategoryName(), subcategoryDtos);
    }

    public final static List<CategoryDto> toCategoryDtos(List<Category> categories){
        return categories.stream().map(c->toCategoryDto(c)).collect(Collectors.toList());
    }



    /* CONVERSORS FOR SUBCATEGORIES */

    public final static List<SubcategoryDto> toSubcategoryDtos(List<Subcategory> subcategories){
        return subcategories.stream().map(c->toSubcategoryDto(c)).collect(Collectors.toList());
    }

    /*
    public static Category toCategory(CategoryDto categoryDto) {
        Set<Subcategory> subcategories = new HashSet<>();
        for (SubcategoryDto subcategoryDto : categoryDto.getSubcategories()) {
            subcategories.add(toSubcategory(subcategoryDto));
        }
        return new Category(categoryDto.getCategoryName(), subcategories);
    }*/

    private static SubcategoryDto toSubcategoryDto(Subcategory subcategory) {
        return new SubcategoryDto(subcategory.getId(), subcategory.getSubcategoryName(), subcategory.getCategory().getId());
    }


    /*
    private static Subcategory toSubcategory(SubcategoryDto subcategoryDto) {
        return new Subcategory(subcategoryDto.getSubcategoryName(), toCategory(subcategoryDto.getParentCategoryId()));
    }*/
}
