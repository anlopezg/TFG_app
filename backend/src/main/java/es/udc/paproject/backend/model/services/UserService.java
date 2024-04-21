package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.entities.User;


public interface UserService {
	
	void signUp(User user) throws DuplicateInstanceException;
	
	User login(String userName, String password) throws IncorrectLoginException;
	
	User loginFromId(Long id) throws InstanceNotFoundException;
	
	User updateProfile(Long id, String username, String email, String firstName, String language, String country, String region,
					   int crochetLevel, int knitLevel, String bio) throws InstanceNotFoundException, DuplicateInstanceException;
	
	void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;


	/**
	 * A user with the common role 'USER' becomes a 'SELLER' role
	 * @param id The user's id
	 * @throws InstanceNotFoundException No user with given id found
	 * @throws UserAlreadySellerException The given user already has the role 'Seller'
	 */
	void userBecomesSeller(Long id) throws InstanceNotFoundException, UserAlreadySellerException;

	User findUserByUsername(String username) throws InstanceNotFoundException;

}
