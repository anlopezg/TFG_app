package es.udc.paproject.backend.model.entities;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{
	
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);
	
}
