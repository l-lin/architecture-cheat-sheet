package lin.louis.clean.usecase;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderNotFoundException;
import lin.louis.clean.usecase.port.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderFinderTest {

	private static final String ORDER_ID = "123";

	@Mock
	private OrderRepository orderRepository;

	private OrderFinder orderFinder;

	@BeforeEach
	void setUp() {
		orderFinder = new OrderFinder(orderRepository);
	}

	@Test
	void shouldReturnAnOrder_whenFound() {
		var order = new Order();
		order.setOrderId(ORDER_ID);
		BDDMockito.given(orderRepository.findById(ORDER_ID)).willReturn(Optional.of(order));

		var orderFound = orderFinder.findById(ORDER_ID);
		Assertions.assertNotNull(orderFound);
		Assertions.assertEquals(ORDER_ID, orderFound.getOrderId());
	}

	@Test
	void shouldThrowOrderNotFoundException_whenNotFound() {
		BDDMockito.given(orderRepository.findById(ORDER_ID)).willReturn(Optional.empty());

		Assertions.assertThrows(OrderNotFoundException.class, () -> orderFinder.findById(ORDER_ID));
	}
}
