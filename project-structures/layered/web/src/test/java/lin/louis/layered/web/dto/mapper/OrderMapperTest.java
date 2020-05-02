package lin.louis.layered.web.dto.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.layered.persistence.entity.Order;
import lin.louis.layered.persistence.entity.OrderStatus;
import lin.louis.layered.persistence.entity.Pet;
import lin.louis.layered.persistence.entity.PetStatus;
import lin.louis.layered.web.dto.OrderDTO;
import lin.louis.layered.web.dto.OrderStatusDTO;
import lin.louis.layered.web.dto.PetDTO;
import lin.louis.layered.web.dto.PetStatusDTO;

class OrderMapperTest {

	private static final long PET_ID = 123;

	private static final String PET_NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final PetMapper PET_MAPPER = new PetMapper();

	private static final long ORDER_ID = 999;

	private static final OrderMapper ORDER_MAPPER = new OrderMapper(PET_MAPPER);

	@Test
	void map_fromDTO() {
		var petDTO = new PetDTO();
		petDTO.setPetId(PET_ID);
		petDTO.setName(PET_NAME);
		petDTO.setPetType(PET_TYPE);
		petDTO.setStatus(PetStatusDTO.AVAILABLE);
		var orderDTO = new OrderDTO();
		orderDTO.setOrderId(ORDER_ID);
		orderDTO.setPet(petDTO);
		orderDTO.setStatus(OrderStatusDTO.DELIVERED);

		var order = ORDER_MAPPER.map(orderDTO);

		Assertions.assertAll(() -> {
			Assertions.assertNotNull(order);
			Assertions.assertEquals(orderDTO.getOrderId(), order.getOrderId());
			Assertions.assertEquals(orderDTO.getStatus().name(), order.getStatus().name());
			var pet = order.getPet();
			Assertions.assertNotNull(pet);
			Assertions.assertEquals(petDTO.getPetId(), pet.getPetId());
			Assertions.assertEquals(petDTO.getName(), pet.getName());
			Assertions.assertEquals(petDTO.getPetType(), pet.getPetType());
			Assertions.assertEquals(petDTO.getStatus().name(), pet.getStatus().name());
		});
	}

	@Test
	void map_fromObject() {
		var pet = new Pet();
		pet.setPetId(PET_ID);
		pet.setName(PET_NAME);
		pet.setPetType(PET_TYPE);
		pet.setStatus(PetStatus.AVAILABLE);
		var order = new Order();
		order.setOrderId(ORDER_ID);
		order.setPet(pet);
		order.setStatus(OrderStatus.DELIVERED);

		var orderDTO = ORDER_MAPPER.map(order);

		Assertions.assertAll(() -> {
			Assertions.assertNotNull(orderDTO);
			Assertions.assertEquals(order.getOrderId(), orderDTO.getOrderId());
			Assertions.assertEquals(order.getStatus().name(), orderDTO.getStatus().name());
			var petDTO = orderDTO.getPet();
			Assertions.assertNotNull(petDTO);
			Assertions.assertEquals(pet.getPetId(), petDTO.getPetId());
			Assertions.assertEquals(pet.getName(), petDTO.getName());
			Assertions.assertEquals(pet.getPetType(), petDTO.getPetType());
			Assertions.assertEquals(pet.getStatus().name(), petDTO.getStatus().name());
		});
	}
}
