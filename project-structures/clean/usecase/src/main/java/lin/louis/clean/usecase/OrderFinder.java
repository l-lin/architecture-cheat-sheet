package lin.louis.clean.usecase;

import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderNotFoundException;
import lin.louis.clean.usecase.port.OrderRepository;

public class OrderFinder {

	private final OrderRepository orderRepository;

	public OrderFinder(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order findById(String orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
	}
}
