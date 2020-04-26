package lin.louis.flat.dao.memory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.flat.dao.PetDAO;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetStatus;

class MemoryPetDAOTest {

	private static final String NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private final PetDAO petDAO = new MemoryPetDAO();

	@Test
	void save() {
		var pet = new Pet();
		pet.setName(NAME);
		pet.setPetType(PET_TYPE);
		pet.setStatus(PetStatus.AVAILABLE);

		long petId = petDAO.save(pet);

		Assertions.assertEquals(1, petId);
	}
}
