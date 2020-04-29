package lin.louis.clean.usecase;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetNotFoundException;
import lin.louis.clean.usecase.port.PetRepository;

public class PetFinder {

	private final PetRepository petRepository;

	public PetFinder(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	public Pet findById(String petId) {
		return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
	}
}
