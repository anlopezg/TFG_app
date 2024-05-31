package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;

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

	User checkSellerUser(String userName) throws InstanceNotFoundException, UserNotSellerException;
	User checkSellerUser(Long userId) throws InstanceNotFoundException, UserNotSellerException;


	Product checkProduct(Long productId) throws InstanceNotFoundException;
	Product checkActiveProduct(Long productId) throws InstanceNotFoundException;

	Product checkProductExistsAndBelongsTo(Long productId, Long userId) throws PermissionException, InstanceNotFoundException;

	ShoppingCart checkCartExistsAndBelongsTo(Long shoppingCartId, Long userId) throws PermissionException, InstanceNotFoundException;

	Purchase checkPurchaseExistsAndBelongsTo(Long purchaseId, Long userId) throws PermissionException, InstanceNotFoundException;

	Review checkReviewExistsAndBelongsTo(Long reviewId, Long userId) throws PermissionException, InstanceNotFoundException;


	/**
	 * Checks a craft exists by its id
	 * @param craftId The craft id
	 * @return The craft
	 * @throws InstanceNotFoundException Craft not found
	 */
	Craft checkCraft(Long craftId) throws InstanceNotFoundException;

	/**
	 * Checks a category exists by its id
	 * @param categoryId The category id
	 * @return The category
	 * @throws InstanceNotFoundException Category not found
	 */
	Subcategory checkSubcategory(Long categoryId) throws InstanceNotFoundException;
}
