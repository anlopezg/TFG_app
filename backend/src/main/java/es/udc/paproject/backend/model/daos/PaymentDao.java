package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDao extends CrudRepository<Payment, Long> {
}
