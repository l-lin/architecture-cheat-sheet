package lin.louis.modular.order.service;

import java.util.Optional;

import lin.louis.modular.order.model.Order;

public interface OrderService {

	Order save(long petId, Order order);

	Optional<Order> findById(long orderId);
}
