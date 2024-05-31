package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotOwnerException;
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

    private final static String USER_NOT_OWNER_EXCEPTION_CODE = "project.exceptions.UserNotOwnerException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private PermissionChecker permissionChecker;


    @ExceptionHandler(UserNotOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleUserNotOwnerException(UserNotOwnerException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(USER_NOT_OWNER_EXCEPTION_CODE, null,
                USER_NOT_OWNER_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }



    /****************************** CREATION OF PRODUCTS ******************************/

    @PostMapping("/physicals/create")
    public ResponseEntity<PhysicalDto> createPhysical(@RequestAttribute Long userId,  @Validated({PhysicalDto.AllValidations.class})
    @RequestBody PhysicalDto physicalDto) throws InstanceNotFoundException, PermissionException,  UserNotSellerException{

        if(!userId.equals(physicalDto.getUserId())){
            throw new PermissionException();
        }

        Physical createdPhysical = publicationService.createPhysical(physicalDto.getUserId(), physicalDto.getCraftId(), physicalDto.getSubcategoryId(),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails(),
                physicalDto.getImagesUrl());

        PhysicalDto createdPhysicalDto = toPhysicalDto(createdPhysical);

        return new ResponseEntity<>(createdPhysicalDto, HttpStatus.CREATED);
    }


    /****************************** FIND ADDED PRODUCTS WITH PAGINATION ******************************/

    @GetMapping("/physicals")
    public BlockDto<PhysicalDto> findAddedPhysicals(@RequestAttribute Long userId, @RequestParam(defaultValue = "0") int page)
            throws InstanceNotFoundException, UserNotSellerException {

        Block<Physical> physicalBlock = publicationService.findAddedPhysicals(userId, page,6 );

        return new BlockDto<>(toPhysicalDtos(physicalBlock.getItems()), physicalBlock.getExistMoreItems());
    }


    /****************************** VIEW DETAILS OF SPECIFIC PRODUCT ******************************/

    @GetMapping("/physicals/{id}")
    public PhysicalDto findPhysicalById(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException, UserNotOwnerException, PermissionException {

        return toPhysicalDto(publicationService.findPhysicalById(userId, id));
    }

    /****************************** EDIT A PRODUCT ******************************/


    @PutMapping("/physicals/edit/{id}")
    public PhysicalDto editPhysical(@RequestAttribute Long userId, @PathVariable Long id,  @Validated({PhysicalDto.AllValidations.class})
    @RequestBody PhysicalDto physicalDto) throws InstanceNotFoundException, UserNotOwnerException, PermissionException {

        return toPhysicalDto(publicationService.editPhysical(id, userId, physicalDto.getCraftId(), physicalDto.getSubcategoryId(),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails(), physicalDto.getImagesUrl()));
    }


    /****************************** DELETE A PRODUCT ******************************/
    @DeleteMapping("/physicals/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhysicalProduct(@RequestAttribute Long userId, @PathVariable Long id)
            throws InstanceNotFoundException, UserNotOwnerException, PermissionException {

        publicationService.deletePhysicalProduct(userId, id);

    }
}
