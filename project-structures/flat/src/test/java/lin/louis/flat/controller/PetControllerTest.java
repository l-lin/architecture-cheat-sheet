package lin.louis.flat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import lin.louis.flat.model.OrderStatus;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetNotAvailableException;
import lin.louis.flat.model.PetNotFoundException;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.OrderService;
import lin.louis.flat.service.PetService;

@WebMvcTest(controllers = PetController.class)
class PetControllerTest {

	private static final long PET_ID = 123;

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetService petService;

	@MockBean
	private OrderService orderService;

	@Nested
	class Save {

		@Test
		void shouldReturnThePet() throws Exception {
			var pet = new Pet();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			var requestBody = objectMapper.writeValueAsString(pet);
			given(petService.save(any(Pet.class))).willReturn(pet);

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
			var pet = new Pet();
			pet.setPetId(PET_ID);
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			given(petService.findById(PET_ID)).willReturn(Optional.of(pet));

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
			given(petService.findById(PET_ID)).willReturn(Optional.empty());

			mockMvc.perform(get("/pets/" + PET_ID).accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));

		}
	}

	@Nested
	class Order {

		@Test
		void shouldReturnAnOrder() throws Exception {
			var order = new lin.louis.flat.model.Order();
			order.setStatus(OrderStatus.PLACED);
			given(orderService.save(anyLong(), any(lin.louis.flat.model.Order.class))).willReturn(order);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_ID + "/orders")
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.CREATED.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				   .andExpect(jsonPath("$.status").value(order.getStatus().name()));
		}

		@Test
		void shouldReturn404_whenPetIsNotFound() throws Exception {
			var order = new lin.louis.flat.model.Order();
			order.setStatus(OrderStatus.PLACED);
			given(orderService.save(
					anyLong(),
					any(lin.louis.flat.model.Order.class)
			)).willThrow(PetNotFoundException.class);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_ID + "/orders")
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}

		@Test
		void shouldReturn400_whenPetIsNotAvailable() throws Exception {
			var order = new lin.louis.flat.model.Order();
			order.setStatus(OrderStatus.PLACED);
			given(orderService.save(anyLong(), any(lin.louis.flat.model.Order.class))).willThrow(
					PetNotAvailableException.class);
			var requestBody = objectMapper.writeValueAsString(order);

			mockMvc.perform(post("/pets/" + PET_ID + "/orders")
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(requestBody))
				   .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
				   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
	}
}
