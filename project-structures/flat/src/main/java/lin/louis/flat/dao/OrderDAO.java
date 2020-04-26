package lin.louis.flat.dao;

import java.util.Optional;

import lin.louis.flat.model.Order;

public interface OrderDAO {

	long save(Order order);

	Optional<Order> findById(long orderId);
}
