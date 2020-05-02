package lin.louis.layered.domain;

import java.util.Optional;

import lin.louis.layered.persistence.entity.Pet;

public interface PetService {

	Pet save(Pet pet);

	Optional<Pet> findById(long petId);
}
