package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.PurchaseDao;
import es.udc.paproject.backend.model.daos.PurchaseItemDao;
import es.udc.paproject.backend.model.daos.ProductDao;
import es.udc.paproject.backend.model.daos.ShoppingCartItemDao;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Purchase purchaseCart(Long userId, Long shoppingCartId, String locality,
                                 String region, String country, String postalAddress, String postalCode) throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException {

        ShoppingCart shoppingCart = permissionChecker.checkCartExistsAndBelongsTo(shoppingCartId, userId);

        if (shoppingCart.isEmpty()) {
            throw new EmptyShoppingCartException();
        }

        Purchase purchase = new Purchase(shoppingCart.getUser(), LocalDateTime.now(), postalAddress, locality, region, country ,postalCode);

        purchaseDao.save(purchase);

        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {

            Product boughtProduct = shoppingCartItem.getProduct();
            int boughtQuantity = shoppingCartItem.getQuantity();

            PurchaseItem purchaseItem = new PurchaseItem(shoppingCartItem.getProduct(),
                    shoppingCartItem.getProduct().getPrice(), shoppingCartItem.getQuantity());

            purchase.addItem(purchaseItem);
            purchaseItemDao.save(purchaseItem);


            //Update product amount when is a physical product
            if(boughtProduct instanceof Physical){
                Physical physical = (Physical) boughtProduct;
                int currentAmount = physical.getAmount();
                physical.setAmount(currentAmount-boughtQuantity);
                productDao.save(physical);
            }

            shoppingCartItemDao.delete(shoppingCartItem);

        }

        shoppingCart.removeAll();

        return purchase;
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
}
