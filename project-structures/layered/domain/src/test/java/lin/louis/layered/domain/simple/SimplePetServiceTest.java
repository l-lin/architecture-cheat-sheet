package lin.louis.layered.domain.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.layered.domain.PetService;
import lin.louis.layered.persistence.PetDAO;
import lin.louis.layered.persistence.memory.MemoryPetDAO;
import lin.louis.layered.persistence.model.Pet;
import lin.louis.layered.persistence.model.PetStatus;

class SimplePetServiceTest {

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private PetDAO petDAO;

	private PetService petService;

	@BeforeEach
	void setUp() {
		petDAO = new MemoryPetDAO();
		petService = new SimplePetService(petDAO);
	}

	@Nested
	class Save {

		@Test
		void shouldReturnThePetWithIdSet() {
			var pet = new Pet();
			pet.setName(NAME);

			var savedPet = petService.save(pet);
			Assertions.assertNotNull(savedPet);
			Assertions.assertEquals(1, savedPet.getPetId());
			Assertions.assertEquals(NAME, savedPet.getName());
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnAPet_whenFound() {
			var pet = new Pet();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			var petId = petDAO.save(pet);

			var petOptional = petService.findById(petId);
			Assertions.assertTrue(petOptional.isPresent());
			var petFound = petOptional.get();
			Assertions.assertEquals(pet.getName(), petFound.getName());
			Assertions.assertEquals(pet.getPetType(), petFound.getPetType());
			Assertions.assertEquals(pet.getStatus(), petFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var petOptional = petService.findById(888);

			Assertions.assertTrue(petOptional.isEmpty());
		}
	}
}
