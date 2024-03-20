package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PermissionChecker;
import es.udc.paproject.backend.model.services.PublicationService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    private final static String USER_NOT_SELLER_EXCEPTION_CODE = "project.exceptions.UserNotSellerException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PermissionChecker permissionChecker;

    @ExceptionHandler(UserNotSellerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleUserNotSellerException(UserNotSellerException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(USER_NOT_SELLER_EXCEPTION_CODE, null,
                USER_NOT_SELLER_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }


    /****************************** CREATION OF PRODUCTS ******************************/
    @PostMapping("/{userName}/patterns")
    public ResponseEntity<PatternDto> createPattern(@RequestAttribute Long userId, @PathVariable String userName,
                                                   @Validated({PatternDto.AllValidations.class}) @RequestBody PatternDto patternDto) throws InstanceNotFoundException, PermissionException, UserNotSellerException {

        // The authenticated user is not the same as the path request one
        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        Pattern createdPattern = publicationService.createPattern(patternDto.getUserId(), patternDto.getCraftId(), patternDto.getSubcategoryId(),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(), patternDto.getSizing(),
                patternDto.getDifficultyLevel(), patternDto.getTime());

        PatternDto createdPatternDto = toPatternDto(createdPattern);

        return new ResponseEntity<>(createdPatternDto, HttpStatus.CREATED);
    }


    @PostMapping("/{userName}/physicals")
    public ResponseEntity<PhysicalDto> createPhysical(@RequestAttribute Long userId, @PathVariable String userName, @Validated({PatternDto.AllValidations.class}) @RequestBody PhysicalDto physicalDto) throws InstanceNotFoundException, PermissionException,  UserNotSellerException{

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        Physical createdPhysical = publicationService.createPhysical(physicalDto.getUserId(), physicalDto.getCraftId(), physicalDto.getSubcategoryId(),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails());

        PhysicalDto createdPhysicalDto = toPhysicalDto(createdPhysical);

        return new ResponseEntity<>(createdPhysicalDto, HttpStatus.CREATED);
    }


    /****************************** FIND ADDED PRODUCTS WITH PAGINATION ******************************/
    /*@GetMapping("/{id}/products")
    public Block<ProductDto> findAddedProducts(@PathVariable Long id, @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page)
            throws PermissionException{

        if(!id.equals(userId)){
            throw new PermissionException();
        }

        Block<Product> productBlock = publicationService.findAddedProducts(userId, page,6 );

        return new Block<>(toProductDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }*/

    @GetMapping("/{userName}/patterns")
    public BlockDto<PatternDto> findAddedPatterns(@RequestAttribute Long userId, @PathVariable String userName, @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException, UserNotSellerException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        Block<Pattern> patternBlock = publicationService.findAddedPatterns(userId, page,6 );

        return new BlockDto<>(toPatternDtos(patternBlock.getItems()), patternBlock.getExistMoreItems());
    }

    @GetMapping("/{userName}/physicals")
    public BlockDto<PhysicalDto> findAddedPhysicals(@RequestAttribute Long userId, @PathVariable String userName, @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        Block<Physical> physicalBlock = publicationService.findAddedPhysicals(userId, page,6 );

        return new BlockDto<>(toPhysicalDtos(physicalBlock.getItems()), physicalBlock.getExistMoreItems());
    }


    /****************************** VIEW DETAILS OF SPECIFIC PRODUCT ******************************/
    /*@GetMapping("/{userName}/products/{id}")
    public ProductDto findProductById(@RequestAttribute Long userId, @PathVariable String userName, @PathVariable Long id) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        return toProductDto(publicationService.findProductById(id));
    }*/

    @GetMapping("/{userName}/patterns/{id}")
    public PatternDto findPatternById(@RequestAttribute Long userId, @PathVariable String userName, @PathVariable Long id) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }


        return toPatternDto(publicationService.findPatternById(id));
    }

    @GetMapping("/{userName}/physicals/{id}")
    public PhysicalDto findPhysicalById(@RequestAttribute Long userId, @PathVariable String userName,@PathVariable Long id) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        return toPhysicalDto(publicationService.findPhysicalById(id));
    }

    /****************************** EDIT A PRODUCT ******************************/
    @PutMapping("/{userName}/patterns/{id}")
    public PatternDto editPattern(@RequestAttribute Long userId, @PathVariable String userName, @PathVariable Long id,  @RequestBody PatternDto patternDto) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        return toPatternDto(publicationService.editPattern(id, userId, patternDto.getCraftId(), patternDto.getSubcategoryId(),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(), patternDto.getSizing(),
                patternDto.getDifficultyLevel(), patternDto.getTime()));
    }

    @PutMapping("/{userName}/physicals/{id}")
    public PhysicalDto editPhysical(@RequestAttribute Long userId, @PathVariable String userName, @PathVariable Long id,  @RequestBody PhysicalDto physicalDto) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        return toPhysicalDto(publicationService.editPhysical(id, userId, physicalDto.getCraftId(), physicalDto.getSubcategoryId(),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails()));
    }


    /****************************** DELETE A PRODUCT ******************************/
    @DeleteMapping("/{userName}/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestAttribute Long userId, @PathVariable String userName, @PathVariable Long id) throws InstanceNotFoundException, PermissionException {

        if(!permissionChecker.checkUserByName(userId, userName)){
            throw new PermissionException();
        }

        publicationService.deleteProduct(id);

    }
}
