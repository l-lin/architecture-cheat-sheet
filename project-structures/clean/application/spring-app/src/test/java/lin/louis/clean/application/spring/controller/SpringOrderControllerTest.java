package lin.louis.clean.application.spring.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lin.louis.clean.adapter.controller.OrderController;
import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.OrderStatusDTO;
import lin.louis.clean.adapter.controller.dto.PetDTO;
import lin.louis.clean.adapter.controller.dto.PetStatusDTO;
import lin.louis.clean.entity.OrderNotFoundException;

@WebMvcTest(controllers = SpringOrderController.class)
class SpringOrderControllerTest {

	private static final String PET_ID = "123";

	private static final String PET_NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final String ORDER_ID = "100";

	private static final String ORDER_NOT_FOUND = "888";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderController orderController;

	@Nested
	class FindById {

		@Test
		void shouldReturnTheOrder_whenFound() throws Exception {
			var pet = new PetDTO();
			pet.setPetId(PET_ID);
			pet.setName(PET_NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatusDTO.AVAILABLE);
			var order = new OrderDTO();
			order.setOrderId(ORDER_ID);
			order.setStatus(OrderStatusDTO.APPROVED);
			order.setPet(pet);
			given(orderController.findById(ORDER_ID)).willReturn(order);

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
			willThrow(new OrderNotFoundException(ORDER_NOT_FOUND))
					.given(orderController)
					.findById(ORDER_NOT_FOUND);

			mockMvc.perform(get("/orders/" + ORDER_NOT_FOUND).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
	}
}
