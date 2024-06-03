package es.udc.paproject.backend.model.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ShoppingCartItemDao shoppingCartItemDao;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private PaymentDao paymentDao;



    @Override
    public ShoppingCart addToCart(Long userId, Long shoppingCartId, Long productId, int quantity) throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        ShoppingCart shoppingCart = permissionChecker.checkCartExistsAndBelongsTo(shoppingCartId, userId);
        Optional<Product> product = productDao.findById(productId);

        if (!product.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        Optional<ShoppingCartItem> existingCartItem = shoppingCart.getItem(productId);

        if (existingCartItem.isPresent()) {
            existingCartItem.get().incrementQuantity(quantity);
        } else {
            ShoppingCartItem newCartItem = new ShoppingCartItem(product.get(), shoppingCart, quantity);
            shoppingCart.addItem(newCartItem);
            shoppingCartItemDao.save(newCartItem);
        }

        return shoppingCart;
    }

    @Override
    public ShoppingCart updateCartItem(Long userId, Long shoppingCartId, Long productId, int quantity) throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        ShoppingCart shoppingCart = permissionChecker.checkCartExistsAndBelongsTo(shoppingCartId, userId);
        Optional<ShoppingCartItem> existingCartItem = shoppingCart.getItem(productId);

        if (!existingCartItem.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        existingCartItem.get().setQuantity(quantity);

        return shoppingCart;
    }

    @Override
    public ShoppingCart removeCartItem(Long userId, Long shoppingCartId, Long productId) throws InstanceNotFoundException, PermissionException {

        ShoppingCart shoppingCart = permissionChecker.checkCartExistsAndBelongsTo(shoppingCartId, userId);
        Optional<ShoppingCartItem> existingCartItem = shoppingCart.getItem(productId);

        if (!existingCartItem.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        shoppingCart.removeItem(existingCartItem.get());
        shoppingCartItemDao.delete(existingCartItem.get());

        return shoppingCart;
    }


    @Override
    public Purchase createPurchase(Long userId, Long shoppingCartId, String locality,
                                   String region, String country, String postalAddress, String postalCode)
            throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException{

        ShoppingCart shoppingCart = permissionChecker.checkCartExistsAndBelongsTo(shoppingCartId, userId);

        if (shoppingCart.isEmpty()) {
            throw new EmptyShoppingCartException();
        }

        Purchase purchase = new Purchase(shoppingCart.getUser(), LocalDateTime.now(), postalAddress, locality, region, country, postalCode);
        purchaseDao.save(purchase);


        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            Product boughtProduct = shoppingCartItem.getProduct();
            int boughtQuantity = shoppingCartItem.getQuantity();

            PurchaseItem purchaseItem = new PurchaseItem(boughtProduct, boughtProduct.getPrice(), boughtQuantity);
            purchase.addItem(purchaseItem);
            purchaseItemDao.save(purchaseItem);


            // Create Payment with an initial state of no-confirmed
            // Associated to a PurchaseItem
            Payment payment = new Payment();
            payment.setPaymentStatus("no_confirmed");
            payment.setPurchaseItem(purchaseItem);
            paymentDao.save(payment);


            purchaseItem.setPayment(payment);
            purchaseItemDao.save(purchaseItem);

            updateProductStock(boughtProduct, boughtQuantity);

            shoppingCartItemDao.delete(shoppingCartItem);
        }

        shoppingCart.removeAll();

        return purchase;
    }


    @Override
    public void processPaymentForPurchase(String paymentMethodId, Purchase purchase)
            throws StripeException, PaymentProcessingException, InstanceNotFoundException {

        List<PurchaseItem> purchaseItems = new ArrayList<>(purchase.getItems());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseItem purchaseItem : purchaseItems) {

            // Payment is processed in cents by Stripe
            BigDecimal itemAmount = purchaseItem.getTotalPrice().multiply(new BigDecimal(100));
            totalAmount = totalAmount.add(itemAmount);

            // Get the stripe account of the owner of the product
            User productOwner = purchaseItem.getProduct().getUser();
            StripeAccount stripeAccount = productOwner.getStripeAccount();
            if (stripeAccount == null) {
                throw new InstanceNotFoundException("Stripe account not found for user: " , productOwner.getId());
            }
            String stripeAccountId = stripeAccount.getStripeAccountId();

            // Create the PaymentIntent for the prodcut
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(itemAmount.longValueExact(), "eur", stripeAccountId, paymentMethodId);
            if (paymentIntent == null || !"succeeded".equals(paymentIntent.getStatus())) {
                throw new PaymentProcessingException("PaymentIntent was not confirmed successfully for product: " + purchaseItem.getProduct().getId() + ". Status: " + (paymentIntent != null ? paymentIntent.getStatus() : "null"));
            }


            // Update the Payment for each Product
            Payment payment = purchaseItem.getPayment();
            payment.setPaymentId(paymentIntent.getId());
            payment.setPaymentMethod(paymentMethodId);
            payment.setPaymentStatus(paymentIntent.getStatus());
            payment.setAmount(itemAmount);
            payment.setCurrency("eur");
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStripeAccountId(stripeAccountId);
            payment.setStripeTransactionId(paymentIntent.getId());


            paymentDao.save(payment);
            purchaseItemDao.save(purchaseItem);

        }

        // Check the total amount paid
        if (!totalAmount.equals(purchase.getTotalPrice().multiply(new BigDecimal(100)))) {
            throw new PaymentProcessingException("The total amount paid does not match the total price of the purchase.");
        }

        purchaseDao.save(purchase);
    }


    @Override
    public Purchase findPurchase(Long userId, Long orderId) throws InstanceNotFoundException, PermissionException {
        return permissionChecker.checkPurchaseExistsAndBelongsTo(orderId, userId);
    }

    @Override
    public Block<Purchase> findPurchases(Long userId, int page, int size) {

        Slice<Purchase> slice = purchaseDao.findByUserIdOrderByDateDesc(userId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }


    private StripeAccount getStripeAccountByProduct(Product product) throws InstanceNotFoundException {

        StripeAccount stripeAccount = product.getUser().getStripeAccount();
        if (stripeAccount == null) {
            throw new InstanceNotFoundException("project.entities.stripeAccount ", stripeAccount.getStripeAccountId());
        }
        return stripeAccount;
    }


    private void updateProductStock(Product product, int boughtQuantity) {
        if (product instanceof Physical) {
            Physical physical = (Physical) product;
            int currentAmount = physical.getAmount();
            physical.setAmount(currentAmount - boughtQuantity);
            productDao.save(physical);
        }
    }


}
