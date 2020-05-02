package lin.louis.clean.application.spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import lin.louis.clean.adapter.controller.PetController;
import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.OrderStatusDTO;
import lin.louis.clean.adapter.controller.dto.PetDTO;
import lin.louis.clean.adapter.controller.dto.PetStatusDTO;
import lin.louis.clean.entity.PetNotAvailableException;
import lin.louis.clean.entity.PetNotFoundException;

@WebMvcTest(controllers = SpringPetController.class)
class SpringPetControllerTest {

	private static final String PET_ID = "123";

	private static final String PET_NOT_FOUND_ID = "888";

	private static final String PET_NOT_AVAILABLE_ID = "999";

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetController petController;

	@Nested
	class Register {

		@Test
		void shouldReturnThePet() throws Exception {
			var pet = new PetDTO();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatusDTO.AVAILABLE);
			var requestBody = objectMapper.writeValueAsString(pet);
			given(petController.register(any(PetDTO.class))).willReturn(pet);

			mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				   .andExpect(status().is(HttpStatus.CREATED.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(jsonPath("$.name").value(pet.getName()))
				   .andExpect(jsonPath("$.petType").value(pet.getPetType()))
				   .andExpect(jsonPath("$.status").value(pet.getStatus().name()));
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnThePet_whenFound() throws Exception {
			var pet = new PetDTO();
			pet.setPetId(PET_ID);
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatusDTO.AVAILABLE);
			given(petController.findById(PET_ID)).willReturn(pet);

			mockMvc.perform(get("/pets/" + PET_ID).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.OK.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(jsonPath("$.petId").value(pet.getPetId()))
				   .andExpect(jsonPath("$.name").value(pet.getName()))
				   .andExpect(jsonPath("$.petType").value(pet.getPetType()))
				   .andExpect(jsonPath("$.status").value(pet.getStatus().name()));

		}

		@Test
		void shouldReturn404_whenNotFound() throws Exception {
			willThrow(new PetNotFoundException(PET_NOT_FOUND_ID))
					.given(petController)
					.findById(PET_NOT_FOUND_ID);

			mockMvc.perform(get("/pets/" + PET_NOT_FOUND_ID).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
	}

	@Nested
	class Order {

		@Test
		void shouldReturnAnOrder() throws Exception {
			var order = new OrderDTO();
			order.setStatus(OrderStatusDTO.PLACED);
			given(petController.order(PET_ID)).willReturn(order);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_ID + "/orders")
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.CREATED.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(jsonPath("$.status").value(order.getStatus().name()));
		}

		@Test
		void shouldReturn404_whenPetIsNotFound() throws Exception {
			var order = new OrderDTO();
			order.setStatus(OrderStatusDTO.PLACED);
			willThrow(new PetNotFoundException(PET_NOT_FOUND_ID))
					.given(petController)
					.order(PET_NOT_FOUND_ID);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_NOT_FOUND_ID + "/orders")
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}

		@Test
		void shouldReturn400_whenPetIsNotAvailable() throws Exception {
			var order = new OrderDTO();
			order.setStatus(OrderStatusDTO.PLACED);
			willThrow(new PetNotAvailableException(PET_NOT_AVAILABLE_ID))
					.given(petController)
					.order(PET_NOT_AVAILABLE_ID);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_NOT_AVAILABLE_ID + "/orders")
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
	}
}
