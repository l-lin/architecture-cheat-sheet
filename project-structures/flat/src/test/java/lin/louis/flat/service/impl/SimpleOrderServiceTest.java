package lin.louis.flat.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import lin.louis.flat.dao.OrderDAO;
import lin.louis.flat.dao.PetDAO;
import lin.louis.flat.dao.impl.MemoryOrderDAO;
import lin.louis.flat.dao.impl.MemoryPetDAO;
import lin.louis.flat.model.Order;
import lin.louis.flat.model.OrderStatus;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetNotAvailableException;
import lin.louis.flat.model.PetNotFoundException;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.OrderService;

class SimpleOrderServiceTest {

	private PetDAO petDAO;

	private OrderDAO orderDAO;

	private OrderService orderService;

	@BeforeEach
	void setUp() {
		petDAO = new MemoryPetDAO();
		orderDAO = new MemoryOrderDAO();
		orderService = new SimpleOrderService(new SimplePetService(petDAO), orderDAO);
	}

	@Nested
	class Save {

		private static final String NAME = "foobar";

		private static final String PET_TYPE = "cat";

		@Test
		void shouldReturnTheOrderWithIdSet_whenPetStatusIsAdequate() {
			// GIVEN
			var pet = new Pet();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			var petId = petDAO.save(pet);
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);

			// WHEN
			var orderSaved = orderService.save(petId, order);

			// THEN
			Assertions.assertNotNull(orderSaved);
			Assertions.assertEquals(1, orderSaved.getOrderId());
			Assertions.assertEquals(order.getStatus(), orderSaved.getStatus());
			Assertions.assertAll(() -> {
				var orderPet = orderSaved.getPet();
				Assertions.assertNotNull(orderPet);
				Assertions.assertEquals(petId, orderPet.getPetId());
				Assertions.assertEquals(pet.getName(), orderPet.getName());
				Assertions.assertEquals(pet.getPetType(), orderPet.getPetType());
				Assertions.assertEquals(pet.getStatus(), orderPet.getStatus());
			});
		}

		@Test
		void shouldThrowPetNotFoundException_whenPetIsNotFound() {
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);

			Assertions.assertThrows(PetNotFoundException.class, () -> orderService.save(888, order));
		}

		@ParameterizedTest
		@EnumSource(value = PetStatus.class, names = { "SOLD", "PENDING" })
		void shouldThrowPetNotAvailable_whenPetStatusIsNotAdequate(PetStatus petStatus) {
			// GIVEN
			var pet = new Pet();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(petStatus);
			var petId = petDAO.save(pet);
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);

			// WHEN
			Assertions.assertAll(
					"save should throw a PetNotAvailableException when pet status is " + petStatus,
					() -> Assertions.assertThrows(
							PetNotAvailableException.class,
							() -> orderService.save(petId, order)
					)
			);
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnAnOrder_whenFound() {
			var order = new Order();
			order.setStatus(OrderStatus.PLACED);
			var orderId = orderDAO.save(order);

			var orderOptional = orderService.findById(orderId);
			Assertions.assertTrue(orderOptional.isPresent());
			var orderFound = orderOptional.get();
			Assertions.assertEquals(order.getStatus(), orderFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var orderOptional = orderService.findById(888);

			Assertions.assertTrue(orderOptional.isEmpty());
		}
	}
}
