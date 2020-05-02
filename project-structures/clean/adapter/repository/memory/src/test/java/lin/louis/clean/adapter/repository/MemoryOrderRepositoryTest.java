package lin.louis.clean.adapter.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderStatus;
import lin.louis.clean.usecase.port.OrderRepository;

class MemoryOrderRepositoryTest {

	private static final String ORDER_ID = "123";

	private OrderRepository orderDAO;

	@BeforeEach
	void setUp() {
		orderDAO = new MemoryOrderRepository();
	}

	@Nested
	class Save {

		@Test
		void shouldReturnTheSameOrder() {
			var order = new Order();
			order.setOrderId(ORDER_ID);
			order.setStatus(OrderStatus.PLACED);

			var orderSaved = orderDAO.save(order);

			Assertions.assertEquals(order.getOrderId(), orderSaved.getOrderId());
			Assertions.assertEquals(order.getStatus(), orderSaved.getStatus());
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnAnOrder_whenFound() {
			var order = new Order();
			order.setOrderId(ORDER_ID);
			order.setStatus(OrderStatus.PLACED);
			var orderSaved = orderDAO.save(order);

			var orderOptional = orderDAO.findById(orderSaved.getOrderId());

			Assertions.assertTrue(orderOptional.isPresent());
			var orderFound = orderOptional.get();
			Assertions.assertEquals(order.getOrderId(), orderFound.getOrderId());
			Assertions.assertEquals(order.getStatus(), orderFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var orderOptional = orderDAO.findById(ORDER_ID);

			Assertions.assertTrue(orderOptional.isEmpty());
		}
	}
}
