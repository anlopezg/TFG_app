package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Craft;
import es.udc.paproject.backend.model.entities.Subcategory;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface CatalogService {

    /**
     * Checks a craft exists by its id
     * @param craftId The craft id
     * @return The craft
     * @throws InstanceNotFoundException Craft not found
     */
    Craft checkCraft(Long craftId) throws InstanceNotFoundException;

    /**
     * Checks a category exists by its id
     * @param categoryId The category id
     * @return The category
     * @throws InstanceNotFoundException Category not found
     */
    Subcategory checkSubcategory(Long categoryId) throws InstanceNotFoundException;

    /**
     *
     * @return All the crafts
     */
    List<Craft> findAllCrafts();

    /**
     *
     * @return All the categories and their subcategories
     */
    List<Category> findAllCategories();

    /**
     *
     * @param categoryId The id of the category
     * @return All the subcategories that belong to the passed category
     */
    List<Subcategory> getSubcategoriesByCategory(Long categoryId);
}
