package lin.louis.modular.order.dao.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import lin.louis.modular.order.dao.OrderDAO;
import lin.louis.modular.order.model.Order;

/**
 * Simple DAO implementation that persists the data in memory.
 */
public class MemoryOrderDAO implements OrderDAO {

	private final AtomicLong idGenerator = new AtomicLong(0);

	private final Map<Long, Order> orders = new ConcurrentHashMap<>();

	@Override
	public long save(Order order) {
		var orderId = idGenerator.incrementAndGet();
		order.setOrderId(orderId);
		orders.put(orderId, order);
		return orderId;
	}

	@Override
	public Optional<Order> findById(long orderId) {
		return Optional.ofNullable(orders.get(orderId));
	}
}
