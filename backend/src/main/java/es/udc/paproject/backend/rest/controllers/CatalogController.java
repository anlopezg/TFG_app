package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.dtos.CategoryDto;
import es.udc.paproject.backend.rest.dtos.CraftDto;
import es.udc.paproject.backend.rest.dtos.SubcategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toSubcategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CraftConversor.toCraftDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/crafts")
    public List<CraftDto> findAllCrafts(){
        return toCraftDtos(catalogService.findAllCrafts());
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories(){

        return toCategoryDtos(catalogService.findAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public List<SubcategoryDto> findSubcategoriesByCategory(@PathVariable Long categoryId){

        return toSubcategoryDtos(catalogService.getSubcategoriesByCategory(categoryId));
    }
}
