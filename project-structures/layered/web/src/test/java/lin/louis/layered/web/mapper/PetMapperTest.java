package lin.louis.layered.web.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.layered.persistence.entity.Pet;
import lin.louis.layered.persistence.entity.PetStatus;
import lin.louis.layered.web.dto.PetDTO;
import lin.louis.layered.web.dto.PetStatusDTO;

class PetMapperTest {

	private static final long PET_ID = 123;

	private static final String PET_NAME = "foobar";

	private static final String PET_TYPE = "cat";

	private static final PetMapper PET_MAPPER = new PetMapper();

	@Test
	void map_fromDTO() {
		var petDTO = new PetDTO();
		petDTO.setPetId(PET_ID);
		petDTO.setName(PET_NAME);
		petDTO.setPetType(PET_TYPE);
		petDTO.setStatus(PetStatusDTO.AVAILABLE);

		var pet = PET_MAPPER.map(petDTO);

		Assertions.assertAll(() -> {
			Assertions.assertNotNull(pet);
			Assertions.assertEquals(petDTO.getPetId(), pet.getPetId());
			Assertions.assertEquals(petDTO.getName(), pet.getName());
			Assertions.assertEquals(petDTO.getPetType(), pet.getPetType());
			Assertions.assertEquals(petDTO.getStatus().name(), pet.getStatus().name());
		});
	}

	@Test
	void map_fromObject() {
		var pet = new Pet();
		pet.setPetId(PET_ID);
		pet.setName(PET_NAME);
		pet.setPetType(PET_TYPE);
		pet.setStatus(PetStatus.AVAILABLE);

		var petDTO = PET_MAPPER.map(pet);

		Assertions.assertAll(() -> {
			Assertions.assertNotNull(petDTO);
			Assertions.assertEquals(pet.getPetId(), petDTO.getPetId());
			Assertions.assertEquals(pet.getName(), petDTO.getName());
			Assertions.assertEquals(pet.getPetType(), petDTO.getPetType());
			Assertions.assertEquals(pet.getStatus().name(), petDTO.getStatus().name());
		});
	}

}
