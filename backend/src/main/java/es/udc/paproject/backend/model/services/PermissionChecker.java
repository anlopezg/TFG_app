package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.User;

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


	/**
	 * Checks if a given username is associated to a user id
	 * @param userId the user id
	 * @param userName the username
	 * @return  True if the userName matches the stored user's username
	 * 			False if they don't match
	 * @throws InstanceNotFoundException There is no user with that userId
	 */
	boolean checkUserByName(Long userId, String userName) throws InstanceNotFoundException;
	
}
