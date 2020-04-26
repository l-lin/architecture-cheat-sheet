package lin.louis.flat.service.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.flat.dao.memory.MemoryPetDAO;
import lin.louis.flat.model.Pet;
import lin.louis.flat.service.PetService;

class SimplePetServiceTest {

	private static final String PET_NAME = "foobar";

	private final PetService petService = new SimplePetService(new MemoryPetDAO());

	@Test
	void save() {
		var pet = new Pet();
		pet.setName(PET_NAME);

		var savedPet = petService.save(pet);
		Assertions.assertNotNull(savedPet);
		Assertions.assertEquals(1, savedPet.getPetId());
		Assertions.assertEquals(PET_NAME, savedPet.getName());
	}
}
