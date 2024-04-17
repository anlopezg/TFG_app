package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CraftConversor.toCraftDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;
import static es.udc.paproject.backend.rest.dtos.UserConversor.toUserDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/crafts")
    public List<CraftDto> findAllCrafts() {
        return toCraftDtos(catalogService.findAllCrafts());
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories() {

        return toCategoryDtos(catalogService.findAllCategories());
    }

    /****************************** PRODUCT SEARCH ******************************/
    @GetMapping("/products")
    public BlockDto<ProductSummaryDto> findProducts(
            @RequestParam(required = false) Long craftId,
            @RequestParam(required = false) Long subcategoryId,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String productType,
            @RequestParam(defaultValue = "0") int page){

        Block<Product> productBlock = catalogService.findProducts(craftId, subcategoryId,
                keywords != null ? keywords.trim() : null, productType, page, 9);

        return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()), productBlock.getExistMoreItems());

    }

    @GetMapping("/products/{id}")
    public ProductDto findProductById(@PathVariable Long id) throws InstanceNotFoundException{

        Product product = catalogService.findProduct(id);

        return toProductDto2(product);
    }


    /****************************** USER PRODUCT SEARCH ******************************/

    @GetMapping("/{username}/products")
    public BlockDto<ProductSummaryDto> findUserProducts(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException, UserNotSellerException {

        Block<Product> productBlock = catalogService.findSellerProducts( username, page, 9);

        return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()), productBlock.getExistMoreItems());

    }

    @GetMapping("/users")
    public BlockDto<UserDto> findUsers(@RequestParam(required = false) String username,
                             @RequestParam(defaultValue = "0") int page){

        Block<User> userBlock = catalogService.findSellers(username, page, 10);

        return new BlockDto<>(toUserDtos(userBlock.getItems()), userBlock.getExistMoreItems());
    }



}
