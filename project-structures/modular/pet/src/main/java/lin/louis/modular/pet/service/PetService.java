package lin.louis.modular.pet.service;

import java.util.Optional;

import lin.louis.modular.pet.model.Pet;

public interface PetService {

	Pet save(Pet pet);

	Optional<Pet> findById(long petId);
}
