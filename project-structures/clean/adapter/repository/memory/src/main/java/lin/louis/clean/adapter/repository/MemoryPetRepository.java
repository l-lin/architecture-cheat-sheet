package lin.louis.clean.adapter.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.usecase.port.PetRepository;

public class MemoryPetRepository implements PetRepository {

	private final Map<String, Pet> pets = new ConcurrentHashMap<>();

	@Override
	public Pet save(Pet pet) {
		pets.put(pet.getPetId(), pet);
		return pet;
	}

	@Override
	public Optional<Pet> findById(String petId) {
		return Optional.ofNullable(pets.get(petId));
	}
}
