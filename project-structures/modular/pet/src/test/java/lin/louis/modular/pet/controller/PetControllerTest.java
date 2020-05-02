package lin.louis.modular.pet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import lin.louis.modular.pet.model.Pet;
import lin.louis.modular.pet.model.PetStatus;
import lin.louis.modular.pet.service.PetService;

@ExtendWith(SpringExtension.class)
class PetControllerTest {

	private static final long PET_ID = 123;

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@MockBean
	private PetService petService;

	@BeforeEach
	void setUp() {
		var petController = new PetController(petService);
		mockMvc = MockMvcBuilders.standaloneSetup(petController).setControllerAdvice(new PetExceptionHandler()).build();
	}

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

}
