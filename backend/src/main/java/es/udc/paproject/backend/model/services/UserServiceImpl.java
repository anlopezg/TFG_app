package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import java.util.Optional;

import es.udc.paproject.backend.model.exceptions.IncorrectLoginException;
import es.udc.paproject.backend.model.exceptions.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;

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
		
		if (userDao.existsByUserName(user.getUserName())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUserName());
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
		
		Optional<User> user = userDao.findByUserName(userName);
		
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
	public User updateProfile(Long id, String email, String firstName, String language, String country, String crochetLevel, String knitLevel, String bio) throws InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(id);

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
	public void deleteProfile(Long id) throws InstanceNotFoundException{

		System.out.println("******* Capa Servicios donde el id a borrar es : "+ id);


		Optional<User> user = userDao.findById(id);

		if(user.isPresent()){
			userDao.delete(user.get());

		}
		else if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", id);
		}


		//Verifies the user exists and returns the current user
		//User user = permissionChecker.checkUser(id);


		System.out.println("******* Justo antes de borrar valor checkUser: "+ user.get().toString());

		//userDao.delete(user);

	}

}
