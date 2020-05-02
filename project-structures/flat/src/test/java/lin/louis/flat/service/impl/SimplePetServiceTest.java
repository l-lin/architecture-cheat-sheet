package lin.louis.flat.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.flat.dao.PetDAO;
import lin.louis.flat.dao.impl.MemoryPetDAO;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.PetService;

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
