package lin.louis.flat.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.flat.dao.OrderDAO;
import lin.louis.flat.model.Order;
import lin.louis.flat.model.OrderStatus;

class MemoryOrderDAOTest {

	private OrderDAO orderDAO;

	@BeforeEach
	void setUp() {
		orderDAO = new MemoryOrderDAO();
	}

	@Nested
	class Save {

		@Test
		void shouldReturnANewOrderId() {
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);

			var orderId = orderDAO.save(order);

			Assertions.assertEquals(1, orderId);
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnAnOrder_whenFound() {
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);
			var orderId = orderDAO.save(order);

			var orderOptional = orderDAO.findById(orderId);

			Assertions.assertTrue(orderOptional.isPresent());
			var orderFound = orderOptional.get();
			Assertions.assertEquals(order.getStatus(), orderFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var orderOptional = orderDAO.findById(888);

			Assertions.assertTrue(orderOptional.isEmpty());
		}
	}
}
