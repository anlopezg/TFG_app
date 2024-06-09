package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotOwnerException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicationServiceImpl implements PublicationService{

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PhysicalDao physicalDao;

    @Autowired
    private ProductImagesDao productImagesDao;


    @Override
    public Physical createPhysical(Long userId, Long craftId, Long subcategoryId, String title, String description,
                                   BigDecimal price, Boolean active, int amount, String size, String color,
                                   String details, List<String> imagesUrl) throws InstanceNotFoundException, UserNotSellerException{

        User user = permissionChecker.checkSellerUser(userId);

        Craft craft = permissionChecker.checkCraft(craftId);
        Subcategory subcategory = permissionChecker.checkSubcategory(subcategoryId);

        LocalDateTime creationDate = LocalDateTime.now();

        Physical physicalCreated = new Physical(user, craft, subcategory,
                title, description, price, active, creationDate, amount, size, color, details);

        productDao.save(physicalCreated);


        for(String imageUrl : imagesUrl){
            ProductImages productImage = new ProductImages(physicalCreated, imageUrl);
            physicalCreated.addImage(productImage);
            productImagesDao.save(productImage);
        }

        return physicalCreated;
    }


    @Override
    @Transactional(readOnly = true)
    public Block<Physical> findAddedPhysicals(Long userId, int page, int size) throws InstanceNotFoundException, UserNotSellerException {

        User user = permissionChecker.checkSellerUser(userId);

        Slice<Physical> slice = physicalDao.findByUser_IdOrderByCreationDateDesc(userId, PageRequest.of(page, size) );

        return new Block<>(slice.getContent(), slice.hasNext());
    }



    @Override
    @Transactional(readOnly=true)
    public Product findProductById(Long productId) throws InstanceNotFoundException{

        Optional<Product> product = productDao.findById(productId);

        if(!product.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        return product.get();
    }


    @Override
    @Transactional(readOnly=true)
    public Physical findPhysicalById(Long userId, Long productId) throws InstanceNotFoundException, PermissionException {

        Optional<Physical> physical = physicalDao.findById(productId);

        if(!physical.isPresent()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        permissionChecker.checkProductExistsAndBelongsTo(productId,userId);

        return physical.get();
    }




    @Override
    public Physical editPhysical(Long productId, Long userId, Long craftId, Long subcategoryId, String title, String description,
                          BigDecimal price, Boolean active, int amount, String size, String color,
                          String details, List<String> imagesUrl) throws InstanceNotFoundException, PermissionException {


        User user = permissionChecker.checkUser(userId);
        Craft craft  = permissionChecker.checkCraft(craftId);
        Subcategory subcategory= permissionChecker.checkSubcategory(subcategoryId);

        Physical physical = findPhysicalById(userId, productId);

        physical.setUser(user);
        physical.setCraft(craft);
        physical.setSubcategory(subcategory);
        physical.setTitle(title);
        physical.setDescription(description);
        physical.setPrice(price);
        physical.setActive(active);
        physical.setAmount(amount);
        physical.setSize(size);
        physical.setColor(color);
        physical.setDetails(details);

        Set<ProductImages> productImages = new HashSet<>();
        for (String imageUrl : imagesUrl) {
            ProductImages productImage = new ProductImages();
            productImage.setImageUrl(imageUrl);
            productImage.setProduct(physical);
            productImages.add(productImage);
        }
        physical.setImages(productImages);

        physicalDao.save(physical);

        return physical;
    }

    @Override
    public void deletePhysical(Long userId, Long productId) throws InstanceNotFoundException, PermissionException {

        Product product = findProductById(productId);

        permissionChecker.checkProductExistsAndBelongsTo(productId, userId);

        Set<ProductImages> images = product.getImages();

        productImagesDao.deleteAll(images);

        productDao.delete(product);
    }
}
