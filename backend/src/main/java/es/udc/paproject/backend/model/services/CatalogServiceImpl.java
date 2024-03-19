package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CraftDao craftDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubcategoryDao subcategoryDao;


    @Override
    public Craft checkCraft(Long craftId) throws InstanceNotFoundException {

        Optional<Craft> craft = craftDao.findById(craftId);

        if(!craft.isPresent()){
            throw new InstanceNotFoundException("project.entities.craft", craftId);
        }

        return craft.get();
    }

    @Override
    public Subcategory checkSubcategory(Long subcategoryId) throws InstanceNotFoundException{

        Optional<Subcategory> subcategory = subcategoryDao.findById(subcategoryId);

        if(!subcategory.isPresent()){
            throw new InstanceNotFoundException("project.entities.subcategory", subcategoryId);
        }

        return subcategory.get();
    }


    @Override
    public List<Craft> findAllCrafts(){
        return craftDao.findAll(Sort.by(Sort.Direction.ASC, "craftName"));
    }

    @Override
    public List<Category> findAllCategories(){
        return categoryDao.findAll(Sort.by(Sort.Direction.ASC, "categoryName"));

    }

    @Override
    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return subcategoryDao.findByCategoryId(categoryId);

    }
}
