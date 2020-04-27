package lin.louis.modular.pet.dao;

import java.util.Optional;

import lin.louis.modular.pet.model.Pet;

public interface PetDAO {

	long save(Pet pet);

	Optional<Pet> findById(long petId);
}
