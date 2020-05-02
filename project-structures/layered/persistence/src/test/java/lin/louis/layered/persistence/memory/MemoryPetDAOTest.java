package lin.louis.layered.persistence.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.layered.persistence.PetDAO;
import lin.louis.layered.persistence.model.Pet;
import lin.louis.layered.persistence.model.PetStatus;

class MemoryPetDAOTest {

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private PetDAO petDAO;

	@BeforeEach
	void setUp() {
		petDAO = new MemoryPetDAO();
	}

	@Nested
	class Save {

		@Test
		void shouldReturnANewPetId() {
			var pet = new Pet();
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);

			var petId = petDAO.save(pet);

			Assertions.assertEquals(1, petId);
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

			var petOptional = petDAO.findById(petId);

			Assertions.assertTrue(petOptional.isPresent());
			var petFound = petOptional.get();
			Assertions.assertEquals(pet.getName(), petFound.getName());
			Assertions.assertEquals(pet.getPetType(), petFound.getPetType());
			Assertions.assertEquals(pet.getStatus(), petFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var petOptional = petDAO.findById(888);

			Assertions.assertTrue(petOptional.isEmpty());
		}
	}
}
