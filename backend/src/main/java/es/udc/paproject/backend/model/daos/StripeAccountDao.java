package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.StripeAccount;
import org.springframework.data.repository.CrudRepository;

public interface StripeAccountDao extends CrudRepository<StripeAccount, Long> {
}
