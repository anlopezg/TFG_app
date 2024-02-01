package es.udc.paproject.backend.test.model.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.IncorrectLoginException;
import es.udc.paproject.backend.model.exceptions.IncorrectPasswordException;
import es.udc.paproject.backend.model.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {
	
	private final Long NON_EXISTENT_ID = Long.valueOf(-1);
	
	@Autowired
	private UserService userService;

	private User createUser(String userName, String email) {
		return new User(userName, email + "@a.com","password", "firstName", "language", "country",
				1, 2, "long bio");
	}
	
	@Test
	public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
		
		User user = createUser("user", "email");
		
		userService.signUp(user);
		
		User loggedInUser = userService.loginFromId(user.getId());
		
		assertEquals(user, loggedInUser);
		assertEquals(User.RoleType.USER, user.getRole());
		
	}
	
	@Test
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
		
		User user = createUser("user", "email");
		
		userService.signUp(user);
		assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));
		
	}

	@Test
	public void testSignUpDuplicatedEmail() throws DuplicateInstanceException {

		User user = createUser("different", "email");

		userService.signUp(user);
		assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));

	}
	
	@Test
	public void testLoginFromNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(NON_EXISTENT_ID));
	}
	
	@Test
	public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {
		
		User user = createUser("user","email");
		String clearPassword = user.getPassword();
				
		userService.signUp(user);
		
		User loggedInUser = userService.login(user.getUserName(), clearPassword);
		
		assertEquals(user, loggedInUser);
		
	}
	
	@Test
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException {
		
		User user = createUser("user","email");
		String clearPassword = user.getPassword();
		
		userService.signUp(user);
		assertThrows(IncorrectLoginException.class, () ->
			userService.login(user.getUserName(), 'X' + clearPassword));
		
	}
	
	@Test
	public void testLoginWithNonExistentUserName() {
		assertThrows(IncorrectLoginException.class, () -> userService.login("X", "Y"));
	}
	
	@Test
	public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {
		
		User user = createUser("user" ,"email");
		
		userService.signUp(user);

		user.setFirstName('X' + user.getFirstName());
		user.setEmail('X' + user.getEmail());
		
		userService.updateProfile(user.getId(), user.getUserName(), 'X' + user.getEmail(),'X' + user.getFirstName(),
				user.getLanguage(), user.getCountry(), user.getCrochetLevel(), user.getKnitLevel(), user.getBio()
			);
		
		User updatedUser = userService.loginFromId(user.getId());
		
		assertEquals(user, updatedUser);
		
	}
	
	@Test
	public void testUpdateProfileWithNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () ->
			userService.updateProfile(NON_EXISTENT_ID, "X", "X@a.com", "X", "language",
					"country", 1, 1, "bio"));
	}
	
	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
		IncorrectPasswordException, IncorrectLoginException {
		
		User user = createUser("user" ,"email");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		
		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUserName(), newPassword);
		
	}
	
	@Test
	public void testChangePasswordWithNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () ->
			userService.changePassword(NON_EXISTENT_ID, "X", "Y"));
	}
	
	@Test
	public void testChangePasswordWithIncorrectPassword() throws DuplicateInstanceException {
		
		User user = createUser("user" ,"email");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		
		userService.signUp(user);
		assertThrows(IncorrectPasswordException.class, () ->
			userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword));
		
	}

}
