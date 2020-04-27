package lin.louis.modular.pet.dao.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import lin.louis.modular.pet.dao.PetDAO;
import lin.louis.modular.pet.model.Pet;

/**
 * Simple DAO implementation that persists the data in memory.
 */
public class MemoryPetDAO implements PetDAO {

	private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

	private final Map<Long, Pet> pets = new ConcurrentHashMap<>();

	@Override
	public long save(Pet pet) {
		var petId = ID_GENERATOR.incrementAndGet();
		pet.setPetId(petId);
		pets.put(petId, pet);
		return petId;
	}

	@Override
	public Optional<Pet> findById(long petId) {
		return Optional.ofNullable(pets.get(petId));
	}
}
