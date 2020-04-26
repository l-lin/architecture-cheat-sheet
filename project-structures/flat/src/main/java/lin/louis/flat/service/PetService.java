package lin.louis.flat.service;

import java.util.Optional;

import lin.louis.flat.model.Pet;

public interface PetService {

	Pet save(Pet pet);

	Optional<Pet> findById(long petId);
}
