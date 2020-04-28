package lin.louis.layered.domain;

import java.util.Optional;

import lin.louis.layered.persistence.model.Pet;

public interface PetService {

	Pet save(Pet pet);

	Optional<Pet> findById(long petId);
}
