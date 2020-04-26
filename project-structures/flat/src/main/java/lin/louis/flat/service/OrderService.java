package lin.louis.flat.service;

import java.util.Optional;

import lin.louis.flat.model.Order;

public interface OrderService {

	Order save(long petId, Order order);

	Optional<Order> findById(long orderId);
}
