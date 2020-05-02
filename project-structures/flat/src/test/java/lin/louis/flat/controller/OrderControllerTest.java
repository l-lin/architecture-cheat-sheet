package lin.louis.flat.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lin.louis.flat.model.Order;
import lin.louis.flat.model.OrderStatus;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.OrderService;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

	private static final long PET_ID = 123;

	private static final String PET_NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final long ORDER_ID = 100;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Nested
	class FindById {

		@Test
		void shouldReturnTheOrder_whenFound() throws Exception {
			var pet = new Pet();
			pet.setPetId(PET_ID);
			pet.setName(PET_NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			var order = new Order();
			order.setOrderId(ORDER_ID);
			order.setStatus(OrderStatus.APPROVED);
			order.setPet(pet);
			given(orderService.findById(ORDER_ID)).willReturn(Optional.of(order));

			mockMvc.perform(get("/orders/" + ORDER_ID).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.OK.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(jsonPath("$.orderId").value(order.getOrderId()))
				   .andExpect(jsonPath("$.status").value(order.getStatus().name()))
				   .andExpect(jsonPath("$.pet.petId").value(pet.getPetId()))
				   .andExpect(jsonPath("$.pet.name").value(pet.getName()))
				   .andExpect(jsonPath("$.pet.petType").value(pet.getPetType()))
				   .andExpect(jsonPath("$.pet.status").value(pet.getStatus().name()));

		}

		@Test
		void shouldReturn404_whenNotFound() throws Exception {
			given(orderService.findById(ORDER_ID)).willReturn(Optional.empty());

			mockMvc.perform(get("/orders/" + ORDER_ID).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
	}
}
