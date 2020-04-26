package lin.louis.flat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.OrderService;
import lin.louis.flat.service.PetService;

@WebMvcTest(controllers = PetController.class)
class PetControllerTest {

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetService petService;

	@MockBean
	private OrderService orderService;

	@Test
	void save() throws Exception {
		var pet = new Pet();
		pet.setName(NAME);
		pet.setPetType(PET_TYPE);
		pet.setStatus(PetStatus.AVAILABLE);
		var requestBody = objectMapper.writeValueAsString(pet);
		given(petService.save(any(Pet.class))).willReturn(pet);

		mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			   .andExpect(status().is(HttpStatus.CREATED.value()))
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("$.name").value(NAME))
			   .andExpect(jsonPath("$.petType").value(PET_TYPE))
			   .andExpect(jsonPath("$.status").value(PetStatus.AVAILABLE.name()));
	}
}
