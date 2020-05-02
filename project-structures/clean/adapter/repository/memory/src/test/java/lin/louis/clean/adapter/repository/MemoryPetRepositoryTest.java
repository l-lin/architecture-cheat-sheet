package lin.louis.clean.adapter.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetStatus;
import lin.louis.clean.usecase.port.PetRepository;

class MemoryPetRepositoryTest {

	private static final String PET_ID = "123";

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private PetRepository petDAO;

	@BeforeEach
	void setUp() {
		petDAO = new MemoryPetRepository();
	}

	@Nested
	class Save {

		@Test
		void shouldReturnANewPetId() {
			var pet = new Pet();
			pet.setPetId(PET_ID);
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);

			var petSaved = petDAO.save(pet);

			Assertions.assertEquals(pet.getPetId(), petSaved.getPetId());
			Assertions.assertEquals(pet.getName(), petSaved.getName());
			Assertions.assertEquals(pet.getPetType(), petSaved.getPetType());
		}
	}

	@Nested
	class FindById {

		@Test
		void shouldReturnAPet_whenFound() {
			var pet = new Pet();
			pet.setPetId(PET_ID);
			pet.setName(NAME);
			pet.setPetType(PET_TYPE);
			pet.setStatus(PetStatus.AVAILABLE);
			var petSaved = petDAO.save(pet);

			var petOptional = petDAO.findById(petSaved.getPetId());

			Assertions.assertTrue(petOptional.isPresent());
			var petFound = petOptional.get();
			Assertions.assertEquals(pet.getName(), petFound.getName());
			Assertions.assertEquals(pet.getPetType(), petFound.getPetType());
			Assertions.assertEquals(pet.getStatus(), petFound.getStatus());
		}

		@Test
		void shouldReturnAnEmptyOptional_whenNotFound() {
			var petOptional = petDAO.findById(PET_ID);

			Assertions.assertTrue(petOptional.isEmpty());
		}
	}
}
