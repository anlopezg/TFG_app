package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Favorite;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import es.udc.paproject.backend.model.services.FavoriteService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.FavoriteDto;
import es.udc.paproject.backend.rest.dtos.ProductSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static es.udc.paproject.backend.rest.dtos.FavoriteDto.toFavoriteDto;
import static es.udc.paproject.backend.rest.dtos.FavoriteDto.toOptionalFavoriteDto;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.toProductDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.toProductSummaryDtos;

@RestController
@RequestMapping("/products")
public class FavoriteController {

    private final static String OWNER_OF_PRODUCT_EXCEPTION_CODE = "project.exceptions.OwnerOfProductException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FavoriteService favoriteService;

    @ExceptionHandler(OwnerOfProductException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleOwnerOfProductException(OwnerOfProductException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(OWNER_OF_PRODUCT_EXCEPTION_CODE, null,
                OWNER_OF_PRODUCT_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteDto> markAsFavorite(@RequestAttribute Long userId, @RequestBody FavoriteDto favoriteDto) throws OwnerOfProductException, InstanceNotFoundException, DuplicateInstanceException {

        Favorite favorite = favoriteService.markAsFavoriteProduct(userId, favoriteDto.getProductId());

        FavoriteDto created = toFavoriteDto(favorite);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/favorites")
    public List<ProductSummaryDto> getFavorites(@RequestAttribute Long userId) throws InstanceNotFoundException {

        List<Product> products = favoriteService.getFavoriteProducts(userId);

        return toProductSummaryDtos(products);

    }

    @GetMapping("/favorites/{productId}")
    public FavoriteDto findFavoriteByUserAndProduct(@RequestAttribute Long userId, @PathVariable Long productId) throws InstanceNotFoundException {

        Optional<Favorite> favorite = favoriteService.findFavoriteByUserAndProduct(userId, productId);

        if(!favorite.isPresent()){

            return new FavoriteDto(userId, productId, false);
        }

        return toOptionalFavoriteDto(favorite);
    }


    // Añadir que se comprueba que el userId, es el que le dio like en una primera instancia
    @DeleteMapping("/favorites/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestAttribute Long userId, @PathVariable Long productId) throws InstanceNotFoundException {

        favoriteService.removeFavoriteProduct(userId, productId);
    }

}
