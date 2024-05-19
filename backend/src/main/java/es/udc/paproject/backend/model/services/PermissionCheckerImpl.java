package es.udc.paproject.backend.model.services;

import java.util.Optional;

import es.udc.paproject.backend.model.daos.*;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.UserNotSellerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

@Service
@Transactional(readOnly=true)
public class PermissionCheckerImpl implements PermissionChecker {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ShoppingCartDao shoppingCartDao;

	@Autowired
	private PurchaseDao purchaseDao;

	@Autowired
	private ReviewDao reviewDao;
	
	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {

		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
	}

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);
		
		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
		return user.get();
		
	}



	@Override
	public User checkSellerUser(Long userId) throws InstanceNotFoundException, UserNotSellerException {

		Optional<User> user = userDao.findById(userId);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

		if(!user.get().getRole().equals(User.RoleType.SELLER)){
			throw new UserNotSellerException();
		}

		return user.get();
	}



	@Override
	public User checkUserName(String username) throws InstanceNotFoundException{

		Optional<User> user = userDao.findByUsername(username);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", username);
		}

		return user.get();

	}

	@Override
	public User checkSellerUser(String username) throws InstanceNotFoundException, UserNotSellerException {

		Optional<User> user = userDao.findByUsername(username);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", username);
		}

		if (!user.get().getRole().equals(User.RoleType.SELLER)) {
			throw new UserNotSellerException();
		}
		return user.get();
	}


	@Override
	public Product checkActiveProduct(Long productId) throws InstanceNotFoundException {

		Optional<Product> product = productDao.findByIdAndActive(productId, true);

		if (!product.isPresent()) {
			throw new InstanceNotFoundException("project.entities.product", productId);
		}

		return product.get();
	}


	@Override

	public Product checkProductExistsAndBelongsTo(Long productId, Long userId) throws PermissionException, InstanceNotFoundException {

		Optional<Product> product = productDao.findById(productId);

		if (!product.isPresent()) {
			throw new InstanceNotFoundException("project.entities.product", productId);
		}

		if(!product.get().getUser().getId().equals(userId)){
			throw new PermissionException();
		}

		return product.get();
	}


	@Override
	public ShoppingCart checkCartExistsAndBelongsTo(Long shoppingCartId, Long userId) throws PermissionException, InstanceNotFoundException {

		Optional<ShoppingCart> shoppingCart = shoppingCartDao.findById(shoppingCartId);

		if (!shoppingCart.isPresent()) {
			throw new InstanceNotFoundException("project.entities.shoppingCart", shoppingCartId);
		}

		if (!shoppingCart.get().getUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return shoppingCart.get();
	}

	@Override
	public Purchase checkPurchaseExistsAndBelongsTo(Long purchaseId, Long userId) throws PermissionException, InstanceNotFoundException {

		Optional<Purchase> purchase = purchaseDao.findById(purchaseId);

		if (!purchase.isPresent()) {
			throw new InstanceNotFoundException("project.entities.purchase", purchaseId);
		}

		if (!purchase.get().getUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return purchase.get();
	}

	@Override
	public Review checkReviewExistsAndBelongsTo(Long reviewId, Long userId) throws PermissionException, InstanceNotFoundException{

		Optional<Review> review = reviewDao.findById(reviewId);

		if (!review.isPresent()) {
			throw new InstanceNotFoundException("project.entities.review", reviewId);
		}

		if(!review.get().getUser().getId().equals(userId)){
			throw new PermissionException();
		}

		return review.get();
	}


}
