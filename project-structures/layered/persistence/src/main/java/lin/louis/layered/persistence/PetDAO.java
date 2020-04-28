package lin.louis.layered.persistence;

import java.util.Optional;

import lin.louis.layered.persistence.model.Pet;

public interface PetDAO {

	long save(Pet pet);

	Optional<Pet> findById(long petId);
}
