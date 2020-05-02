package lin.louis.clean.usecase;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetNotFoundException;
import lin.louis.clean.usecase.port.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetFinderTest {

	private static final String PET_ID = "123";

	@Mock
	private PetRepository petRepository;

	private PetFinder petFinder;

	@BeforeEach
	void setUp() {
		petFinder = new PetFinder(petRepository);
	}

	@Test
	void shouldReturnAPet_whenFound() {
		var pet = new Pet();
		pet.setPetId(PET_ID);
		BDDMockito.given(petRepository.findById(PET_ID)).willReturn(Optional.of(pet));

		var petFound = petFinder.findById(PET_ID);
		Assertions.assertNotNull(petFound);
		Assertions.assertEquals(PET_ID, petFound.getPetId());
	}

	@Test
	void shouldThrowPetNotFoundException_whenNotFound() {
		BDDMockito.given(petRepository.findById(PET_ID)).willReturn(Optional.empty());

		Assertions.assertThrows(PetNotFoundException.class, () -> petFinder.findById(PET_ID));
	}
}
