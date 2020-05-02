package lin.louis.clean.usecase;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderStatus;
import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetNotAvailableException;
import lin.louis.clean.entity.PetNotFoundException;
import lin.louis.clean.entity.PetStatus;
import lin.louis.clean.usecase.port.IdGenerator;
import lin.louis.clean.usecase.port.OrderRepository;
import lin.louis.clean.usecase.port.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetOrdererTest {

	private static final String PET_ID = "123";

	private static final String ID = "999";

	@Mock
	private IdGenerator idGenerator;

	@Mock
	private PetRepository petRepository;

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private PetOrderer petOrderer;

	@Test
	void shouldReturnAndOrder_whenPetIsFound_andPetStatusIsAdequate() {
		// GIVEN
		BDDMockito.given(idGenerator.generate()).willReturn(ID);
		var pet = new Pet();
		pet.setStatus(PetStatus.AVAILABLE);
		BDDMockito.given(petRepository.findById(PET_ID)).willReturn(Optional.of(pet));
		BDDMockito.given(orderRepository.save(Mockito.any(Order.class)))
				  .willAnswer(invocation -> invocation.getArgument(0));

		// WHEN
		var order = petOrderer.order(PET_ID);

		// THEN
		Assertions.assertNotNull(order);
		Assertions.assertEquals(ID, order.getOrderId());
		Assertions.assertEquals(OrderStatus.PLACED, order.getStatus());
	}

	@Test
	void shouldThrowPetNotFoundException_whenPetIsNotFound() {
		BDDMockito.given(petRepository.findById(PET_ID)).willReturn(Optional.empty());

		Assertions.assertThrows(PetNotFoundException.class, () -> petOrderer.order(PET_ID));
	}

	@Test
	void shouldThrowPetNotAvailable_whenPetIsNotAvailable() {
		var pet = new Pet();
		pet.setStatus(PetStatus.SOLD);
		BDDMockito.given(petRepository.findById(PET_ID)).willReturn(Optional.of(pet));

		Assertions.assertThrows(PetNotAvailableException.class, () -> petOrderer.order(PET_ID));
	}
}
