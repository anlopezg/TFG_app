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
                             @Validated @RequestBody PurchaseParamsDto params) throws PermissionException, EmptyShoppingCartException, InstanceNotFoundException, StripeException {

        Purchase purchaseCreated = purchaseService.createPurchase(userId, shoppingCartId, params.getPostalAddress(), params.getLocality(),
                params.getRegion(), params.getCountry(), params.getPostalCode());

        return toPurchasePaymentDto(purchaseCreated);

    }


    @PostMapping("/purchases/{purchaseId}/processPayment")
    public ResponseEntity<Void> processPaymentForPurchase(@RequestAttribute Long userId, @PathVariable Long purchaseId,
                                                          @Validated @RequestBody PaymentParamsDto paymentParams)
            throws InstanceNotFoundException, StripeException, PaymentProcessingException, PermissionException {

        Purchase purchase = purchaseService.findPurchase(userId, purchaseId);
        purchaseService.processPaymentForPurchase(paymentParams.getPaymentMethodId(), purchase);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/purchases/{purchaseId}")
    public PurchaseDto findPurchase(@RequestAttribute Long userId, @PathVariable Long purchaseId) throws InstanceNotFoundException, PermissionException{

        return toPurchaseDto(purchaseService.findPurchase(userId, purchaseId));
    }

    @GetMapping("/purchases")
    public BlockDto<PurchaseSummaryDto> findPurchases(@RequestAttribute Long userId, @RequestParam(defaultValue = "0") int page){

        Block<Purchase> purchaseBlock = purchaseService.findPurchases(userId, page, 10);

        return new BlockDto<>(toPurchaseSummaryDtos(purchaseBlock.getItems()), purchaseBlock.getExistMoreItems());
    }

    /*
    @PostMapping("/create")
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDto purchaseRequest) {
        Purchase purchase = new Purchase();
        // Configurar otros campos de Purchase desde purchaseRequest
        purchase.setPaymentStatus(PaymentStatus.PENDING);

        purchase = purchaseService.save(purchase); // Guardar la compra en la base de datos
        return ResponseEntity.ok(purchase);
    }

    @PostMapping("/updatePaymentStatus")
    public ResponseEntity<Purchase> updatePaymentStatus(@RequestParam Long purchaseId, @RequestParam PaymentStatus status) {
        Purchase purchase = purchaseService.findById(purchaseId);
        if (purchase != null) {
            purchase.setPaymentStatus(status);
            purchaseService.save(purchase);
            return ResponseEntity.ok(purchase);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }/*

     */
}
