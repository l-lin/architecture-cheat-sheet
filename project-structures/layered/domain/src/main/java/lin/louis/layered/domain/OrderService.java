package lin.louis.layered.domain;

import java.util.Optional;

import lin.louis.layered.persistence.model.Order;

public interface OrderService {

	Order save(long petId, Order order);

	Optional<Order> findById(long orderId);
}
