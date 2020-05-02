package lin.louis.layered.domain.simple;

import java.util.Optional;

import lin.louis.layered.domain.PetService;
import lin.louis.layered.persistence.PetDAO;
import lin.louis.layered.persistence.entity.Pet;

/**
 * A simple implementation that encapsulate a {@link PetDAO}, so we can easily switch the persistence layer without
 * pain, for example if we want to use an external database, we don't need to change the service, which is the purpose
 * of the strategy pattern.
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
