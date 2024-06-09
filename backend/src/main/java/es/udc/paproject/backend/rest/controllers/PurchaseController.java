package es.udc.paproject.backend.rest.controllers;

import com.stripe.exception.StripeException;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PurchaseService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.PurchaseConversor.*;
import static es.udc.paproject.backend.rest.dtos.ShoppingCartConversor.toShoppingCartDto;

@RestController
@RequestMapping("/shopping")
public class PurchaseController {

    private final static String MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxQuantityExceededException";
    private final static String MAX_ITEMS_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxItemsExceededException";
    private final static String EMPTY_SHOPPING_CART_EXCEPTION_CODE = "project.exceptions.EmptyShoppingCartException";
    private static final String STRIPE_EXCEPTION_CODE = "project.exceptions.StripeException";
    private static final String PAYMENT_PROCESSING_EXCEPTION_CODE = "project.exceptions.PaymentProcessingException";

    private static final String PAYMENT_ALREADY_PROCESSED_EXCEPTION_CODE = "project.exceptions.PaymentAlreadyProcessedException";



    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PurchaseService purchaseService;

    @ExceptionHandler(MaxQuantityExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxQuantityExceededException(MaxQuantityExceededException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE,
                new Object[] {exception.getMaxAllowedIncrement()}, MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(MaxItemsExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxItemsExceededException(MaxItemsExceededException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(MAX_ITEMS_EXCEEDED_EXCEPTION_CODE, null,
                MAX_ITEMS_EXCEEDED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(EmptyShoppingCartException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleEmptyShoppingCartException(EmptyShoppingCartException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(EMPTY_SHOPPING_CART_EXCEPTION_CODE, null,
                EMPTY_SHOPPING_CART_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(StripeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handleStripeException(StripeException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(STRIPE_EXCEPTION_CODE, new Object[]{exception.getMessage()}, STRIPE_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(PaymentProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handlePaymentProcessingException(PaymentProcessingException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(PAYMENT_PROCESSING_EXCEPTION_CODE, new Object[]{exception.getMessage()}, PAYMENT_PROCESSING_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorsDto handlePaymentAlreadyProcessedException(PaymentAlreadyProcessedException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(PAYMENT_ALREADY_PROCESSED_EXCEPTION_CODE, new Object[]{exception.getMessage()}, PAYMENT_ALREADY_PROCESSED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }



    @PostMapping("/carts/{shoppingCartId}/addItem")
    public ShoppingCartDto addItemToCart(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                                     @Validated @RequestBody ShoppingCartParamsDto params) throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        return toShoppingCartDto(purchaseService.addToCart(userId, shoppingCartId, params.getProductId(), params.getQuantity()));

    }

    @PostMapping("/carts/{shoppingCartId}/updateItem")
    public ShoppingCartDto updateCartItem(@RequestAttribute Long userId,
                                          @PathVariable Long shoppingCartId, @RequestBody ShoppingCartParamsDto params) throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        return toShoppingCartDto(purchaseService.updateCartItem(userId, shoppingCartId, params.getProductId(), params.getQuantity()));
    }

    @PostMapping("/carts/{shoppingCartId}/removeItem")
    public ShoppingCartDto removeCartItem(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                                          @RequestBody ShoppingCartParamsDto params) throws InstanceNotFoundException, PermissionException{

        return toShoppingCartDto(purchaseService.removeCartItem(userId, shoppingCartId, params.getProductId()));

    }

    @PostMapping ("/carts/{shoppingCartId}/purchase")
    public PurchasePaymentDto purchaseCart(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                             @Validated @RequestBody PurchaseParamsDto params) throws PermissionException, EmptyShoppingCartException, InstanceNotFoundException, StripeException, MaxItemsExceededException {

        Purchase purchaseCreated = purchaseService.createPurchase(userId, shoppingCartId, params.getPostalAddress(), params.getLocality(),
                params.getRegion(), params.getCountry(), params.getPostalCode());

        return toPurchasePaymentDto(purchaseCreated);

    }


    @PostMapping("/purchases/{purchaseId}/processPayment")
    public ResponseEntity<Void> processPaymentForPurchase(@RequestAttribute Long userId, @PathVariable Long purchaseId,
                                                          @Validated @RequestBody PaymentParamsDto paymentParams)
            throws InstanceNotFoundException, StripeException, PaymentProcessingException, PermissionException, PaymentAlreadyProcessedException {

        System.out.println("******************* Received purchaseId: " + purchaseId);

        Purchase purchase = purchaseService.findPurchaseById(userId, purchaseId);
        purchaseService.processPaymentForPurchase(paymentParams.getPaymentMethodId(), purchase);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/purchases/{purchaseId}")
    public PurchaseDto findPurchase(@RequestAttribute Long userId, @PathVariable Long purchaseId) throws InstanceNotFoundException, PermissionException{

        return toPurchaseDto(purchaseService.findPurchaseById(userId, purchaseId));
    }

    @GetMapping("/purchases")
    public BlockDto<PurchaseSummaryDto> findPurchases(@RequestAttribute Long userId, @RequestParam(defaultValue = "0") int page){

        Block<Purchase> purchaseBlock = purchaseService.findPurchases(userId, page, 10);

        return new BlockDto<>(toPurchaseSummaryDtos(purchaseBlock.getItems()), purchaseBlock.getExistMoreItems());
    }

}
