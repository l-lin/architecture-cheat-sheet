package lin.louis.modular.pet.service.impl;

import java.util.Optional;

import lin.louis.modular.pet.dao.PetDAO;
import lin.louis.modular.pet.model.Pet;
import lin.louis.modular.pet.service.PetService;

/**
 * A simple implementation that encapsulates a {@link lin.louis.modular.pet.dao.PetDAO}, so we can easily switch the
 * persistence layer without pain, for example if we want to use an external database, we don't need to change the
 * service.
 */
public class SimplePetService implements PetService {

	private final PetDAO petDAO;

	public SimplePetService(PetDAO petDAO) {
		this.petDAO = petDAO;
	}

	public Pet save(Pet pet) {
		var petId = petDAO.save(pet);
		pet.setPetId(petId);
		return pet;
	}

	public Optional<Pet> findById(long petId) {
		return petDAO.findById(petId);
	}
}
