package lin.louis.layered.persistence.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.layered.persistence.OrderDAO;
import lin.louis.layered.persistence.model.Order;
import lin.louis.layered.persistence.model.OrderStatus;

class MemoryOrderDAOTest {

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

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
		@org.junit.jupiter.api.Order(2)
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var orderOptional = orderDAO.findById(888);

			Assertions.assertTrue(orderOptional.isEmpty());
		}
	}
}
