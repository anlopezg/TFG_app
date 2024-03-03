package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PublicationService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toSubcategoryDtos;
import static es.udc.paproject.backend.rest.dtos.CraftConversor.toCraftDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    private final static String USER_NOT_SELLER_EXCEPTION_CODE = "project.exceptions.UserNotSellerException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PublicationService publicationService;

    @ExceptionHandler(UserNotSellerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleUserNotSellerException(UserNotSellerException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(USER_NOT_SELLER_EXCEPTION_CODE, null,
                USER_NOT_SELLER_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @PostMapping("/patterns")
    public ResponseEntity<PatternDto> createPattern(@RequestAttribute Long userId,
           @Validated({PatternDto.AllValidations.class}) @RequestBody PatternDto patternDto)
            throws InstanceNotFoundException, PermissionException, UserNotSellerException {

        if(!patternDto.getUserId().equals(userId)){
            throw new PermissionException();
        }

        Pattern createdPattern = publicationService.createPattern(patternDto.getUserId(), patternDto.getCraftId(), patternDto.getSubcategoryId(),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(), patternDto.getSizing(),
                patternDto.getDifficultyLevel(), patternDto.getTime());

        PatternDto createdPatternDto = toPatternDto(createdPattern);

        return new ResponseEntity<>(createdPatternDto, HttpStatus.CREATED);
    }

    @PostMapping("/physicals")
    public ResponseEntity<PhysicalDto> createPhysical(@RequestAttribute Long userId,
            @Validated({PatternDto.AllValidations.class}) @RequestBody PhysicalDto physicalDto)
            throws InstanceNotFoundException, PermissionException,  UserNotSellerException{

        if(!physicalDto.getUserId().equals(userId)){
            throw new PermissionException();
        }

        Physical createdPhysical = publicationService.createPhysical(physicalDto.getUserId(), physicalDto.getCraftId(), physicalDto.getSubcategoryId(),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails());

        PhysicalDto createdPhysicalDto = toPhysicalDto(createdPhysical);

        return new ResponseEntity<>(createdPhysicalDto, HttpStatus.CREATED);
    }



    /* Cambiar a catalog controller ?*/
    @GetMapping("/crafts")
    public List<CraftDto> findAllCrafts(){
        return toCraftDtos(publicationService.findAllCrafts());
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories(){

        return toCategoryDtos(publicationService.findAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public List<SubcategoryDto> findSubcategoriesByCategory(@PathVariable Long categoryId){

        return toSubcategoryDtos(publicationService.getSubcategoriesByCategory(categoryId));
    }


    /*
    @GetMapping("/products")
    public Block<ProductDto> findProducts(@RequestParam(required=false) Long categoryId,
                                          @RequestParam(required=false) String keywords,
                                          @RequestParam(defaultValue="0") int page){

        Block<Product> productBlock = publicationService.findProducts(categoryId, keywords, page, 10);

        return new Block<>(toProductDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }*/




    @GetMapping("/{id}/products")
    public Block<ProductDto> findAddedProducts(@PathVariable Long id, @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page)
            throws PermissionException{

        if(!id.equals(userId)){
            throw new PermissionException();
        }

        Block<Product> productBlock = publicationService.findAddedProducts(userId, page,10 );

        return new Block<>(toProductDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }

    @GetMapping("/patterns")
    public BlockDto<PatternDto> findAddedPatterns(@RequestAttribute Long userId,
                                               @RequestParam(defaultValue = "0") int page){

        Block<Pattern> patternBlock = publicationService.findAddedPatterns(userId, page,10 );

        return new BlockDto<>(toPatternDtos(patternBlock.getItems()), patternBlock.getExistMoreItems());
    }

    @GetMapping("/physicals")
    public BlockDto<PhysicalDto> findAddedPhysicals(@RequestAttribute Long userId,
                                               @RequestParam(defaultValue = "0") int page){


        Block<Physical> physicalBlock = publicationService.findAddedPhysicals(userId, page,10 );

        return new BlockDto<>(toPhysicalDtos(physicalBlock.getItems()), physicalBlock.getExistMoreItems());
    }

    @GetMapping("/products/{id}")
    public ProductDto findProductById(@PathVariable Long id) throws InstanceNotFoundException{
        return toProductDto(publicationService.findProductById(id));
    }

    @GetMapping("/patterns/{id}")
    public PatternDto findPatternById(@PathVariable Long id) throws InstanceNotFoundException{
        return toPatternDto(publicationService.findPatternById(id));
    }



}
