package lin.louis.clean.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetStatus;
import lin.louis.clean.usecase.port.IdGenerator;
import lin.louis.clean.usecase.port.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetRegisterTest {

	private static final String PET_ID = "123";

	private static final String PET_TYPE = "cat";

	private static final String PET_NAME = "Nyan cat";

	@Mock
	private PetRepository petRepository;

	private PetRegister petRegister;

	@BeforeEach
	void setUp() {
		petRegister = new PetRegister(new FakeIdGenerator(), petRepository);
	}

	@Test
	void register() {
		BDDMockito.given(petRepository.save(Mockito.any(Pet.class)))
				  .willAnswer(invocation -> invocation.getArgument(0));
		var pet = new Pet();
		pet.setPetType(PET_TYPE);
		pet.setName(PET_NAME);
		pet.setStatus(PetStatus.AVAILABLE);

		var petRegistered = petRegister.register(pet);

		Assertions.assertNotNull(petRegistered);
		Assertions.assertEquals(PET_ID, petRegistered.getPetId());
		Assertions.assertEquals(pet.getPetType(), petRegistered.getPetType());
		Assertions.assertEquals(pet.getName(), petRegistered.getName());
		Assertions.assertEquals(pet.getStatus(), petRegistered.getStatus());
		BDDMockito.then(petRepository).should().save(Mockito.any(Pet.class));
	}

	private static class FakeIdGenerator implements IdGenerator {

		@Override
		public String generate() {
			return PET_ID;
		}
	}
}
