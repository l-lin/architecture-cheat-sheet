package lin.louis.layered.domain;

import java.util.Optional;

import lin.louis.layered.persistence.entity.Order;

public interface OrderService {

	Order save(long petId, Order order);

	Optional<Order> findById(long orderId);
}
