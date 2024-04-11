package es.udc.paproject.backend.model.services;

import java.util.Objects;
import java.util.Optional;

import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.daos.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void signUp(User user) throws DuplicateInstanceException {
		
		if (userDao.existsByUsername(user.getUsername())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUsername());
		}

		if (userDao.existsByEmail(user.getEmail())){
			throw new DuplicateInstanceException("project.entities.user", user.getEmail());
		}
			
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(User.RoleType.USER);
		
		userDao.save(user);
		
	}

	@Override
	@Transactional(readOnly=true)
	public User login(String userName, String password) throws IncorrectLoginException {
		
		Optional<User> user = userDao.findByUsername(userName);
		
		if (!user.isPresent()) {
			throw new IncorrectLoginException(userName, password);
		}
		
		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectLoginException(userName, password);
		}
		
		return user.get();
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public User loginFromId(Long id) throws InstanceNotFoundException {
		return permissionChecker.checkUser(id);
	}

	@Override
	public User updateProfile(Long id, String username, String email, String firstName, String language, String country, int crochetLevel, int knitLevel, String bio) throws InstanceNotFoundException, DuplicateInstanceException {
		
		User user = permissionChecker.checkUser(id);

		//When the username is modified from the one stored, the new one has to be unique
		if (!Objects.equals(user.getUsername(), username) && userDao.existsByUsername(username)) {
			throw new DuplicateInstanceException("project.entities.user", username);
		}

		//Same applies to the email
		if (!Objects.equals(user.getEmail(), email) && userDao.existsByEmail(email)){
			throw new DuplicateInstanceException("project.entities.user", email);
		}


		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLanguage(language);
		user.setCountry(country);
		user.setCrochetLevel(crochetLevel);
		user.setKnitLevel(knitLevel);
		user.setBio(bio);

		return user;

	}

	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException {
		
		User user = permissionChecker.checkUser(id);
		
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		
	}

	@Override
	public void userBecomesSeller(Long id) throws InstanceNotFoundException, UserAlreadySellerException {

		User user = permissionChecker.checkUser(id);

		if(user.getRole() == User.RoleType.SELLER){
			throw new UserAlreadySellerException();
		}

		user.setRole(User.RoleType.SELLER);
	}

	@Override
	@Transactional(readOnly=true)
	public User findUserByUsername(String username) throws InstanceNotFoundException{

        return permissionChecker.checkUserName(username);

	}

}
