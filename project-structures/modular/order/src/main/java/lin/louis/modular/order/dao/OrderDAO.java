package lin.louis.modular.order.dao;

import java.util.Optional;

import lin.louis.modular.order.model.Order;

public interface OrderDAO {

	long save(Order order);

	Optional<Order> findById(long orderId);
}
