package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ShoppingCart;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.PermissionException;

public interface PermissionChecker {
	
	void checkUserExists(Long userId) throws InstanceNotFoundException;
	
	User checkUser(Long userId) throws InstanceNotFoundException;


	/**
	 * Finds a User by its username
	 * @param userName The user's username
	 * @return The found user
	 * @throws InstanceNotFoundException There is no user with that userName
	 */
	User checkUserName(String userName) throws InstanceNotFoundException;


	Product checkProductExistsAndBelongsTo(Long productId, Long userId) throws PermissionException, InstanceNotFoundException;

	ShoppingCart checkCartExistsAndBelongsTo(Long shoppingCartId, Long userId) throws PermissionException, InstanceNotFoundException;

	Purchase checkPurchaseExistsAndBelongsTo(Long purchaseId, Long userId) throws PermissionException, InstanceNotFoundException;


	
}
