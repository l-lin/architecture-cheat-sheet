package lin.louis.clean.adapter.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lin.louis.clean.entity.Order;
import lin.louis.clean.usecase.port.OrderRepository;

public class MemoryOrderRepository implements OrderRepository {

	private final Map<String, Order> orders = new ConcurrentHashMap<>();

	@Override
	public Order save(Order order) {
		orders.put(order.getOrderId(), order);
		return order;
	}

	@Override
	public Optional<Order> findById(String orderId) {
		return Optional.ofNullable(orders.get(orderId));
	}
}
