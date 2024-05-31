package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Favorite;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CraftConversor.toCraftDtos;
import static es.udc.paproject.backend.rest.dtos.FavoriteDto.toFavoriteDto;
import static es.udc.paproject.backend.rest.dtos.FavoriteDto.toOptionalFavoriteDto;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;
import static es.udc.paproject.backend.rest.dtos.UserConversor.toUserDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final static String OWNER_OF_PRODUCT_EXCEPTION_CODE = "project.exceptions.OwnerOfProductException";

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(OwnerOfProductException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleOwnerOfProductException(OwnerOfProductException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(OWNER_OF_PRODUCT_EXCEPTION_CODE, null,
                OWNER_OF_PRODUCT_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

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

        return toProductDto(product);
    }


    /****************************** USER PRODUCT SEARCH ******************************/

    @GetMapping("/users/{username}/products")
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


    @PostMapping("/favorites")
    public ResponseEntity<FavoriteDto> markAsFavorite(@RequestAttribute Long userId, @RequestBody FavoriteDto favoriteDto) throws OwnerOfProductException, InstanceNotFoundException, DuplicateInstanceException {

        Favorite favorite = catalogService.markAsFavoriteProduct(userId, favoriteDto.getProductId());

        FavoriteDto created = toFavoriteDto(favorite);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/favorites")
    public List<ProductSummaryDto> getFavorites(@RequestAttribute Long userId) throws InstanceNotFoundException {

        List<Product> products = catalogService.getFavoriteProducts(userId);

        return toProductSummaryDtos(products);

    }

    @GetMapping("/favorites/{productId}")
    public FavoriteDto findFavoriteByUserAndProduct(@RequestAttribute Long userId, @PathVariable Long productId) throws InstanceNotFoundException {

        Optional<Favorite> favorite = catalogService.findFavoriteByUserAndProduct(userId, productId);

        if(!favorite.isPresent()){

            return new FavoriteDto(userId, productId, false);
        }

        return toOptionalFavoriteDto(favorite);
    }


    // Añadir que se comprueba que el userId, es el que le dio like en una primera instancia
    @DeleteMapping("/favorites/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestAttribute Long userId, @PathVariable Long productId) throws InstanceNotFoundException {

        catalogService.removeFavoriteProduct(userId, productId);
    }


}
