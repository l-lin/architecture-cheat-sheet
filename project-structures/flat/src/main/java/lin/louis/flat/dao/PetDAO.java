package lin.louis.flat.dao;

import java.util.Optional;

import lin.louis.flat.model.Pet;

public interface PetDAO {

	long save(Pet pet);

	Optional<Pet> findById(long petId);
}
