package es.udc.paproject.backend.model.daos;

import java.util.Optional;

import es.udc.paproject.backend.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>, CustomizedUserDao {
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
}
