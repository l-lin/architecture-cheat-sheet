package lin.louis.layered.persistence.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import lin.louis.layered.persistence.PetDAO;
import lin.louis.layered.persistence.entity.Pet;

/**
 * Simple DAO implementation that persists the data in memory.
 */
public class MemoryPetDAO implements PetDAO {

	private final AtomicLong idGenerator = new AtomicLong(0);

	private final Map<Long, Pet> pets = new ConcurrentHashMap<>();

	@Override
	public long save(Pet pet) {
		var petId = idGenerator.incrementAndGet();
		pet.setPetId(petId);
		pets.put(petId, pet);
		return petId;
	}

	@Override
	public Optional<Pet> findById(long petId) {
		return Optional.ofNullable(pets.get(petId));
	}
}
