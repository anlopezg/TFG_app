package es.udc.paproject.backend.model.services;

import com.stripe.exception.StripeException;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.ShoppingCart;
import es.udc.paproject.backend.model.exceptions.*;

public interface PurchaseService {

    ShoppingCart addToCart(Long userId, Long shoppingCartId, Long productId, int quantity)
        throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException;

    ShoppingCart updateCartItem(Long userId, Long shoppingCartId, Long productId, int quantity)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException;

    ShoppingCart removeCartItem(Long userId, Long shoppingCartId, Long productId)
        throws InstanceNotFoundException, PermissionException;

    Purchase createPurchase(Long userId, Long shoppingCartId, String postalAddress, String locality,
                            String region, String country, String postalCode)
            throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException, StripeException, MaxItemsExceededException;

    void processPaymentForPurchase(String paymentMethodId, Purchase purchase) throws StripeException, PaymentProcessingException, InstanceNotFoundException, PaymentAlreadyProcessedException;

    Purchase findPurchaseById(Long userId, Long orderId) throws InstanceNotFoundException, PermissionException;

    Block<Purchase> findPurchases(Long userId, int page, int size);

}

