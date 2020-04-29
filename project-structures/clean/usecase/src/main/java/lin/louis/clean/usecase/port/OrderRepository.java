package lin.louis.clean.usecase.port;

import java.util.Optional;

import lin.louis.clean.entity.Order;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findById(String orderId);
}
