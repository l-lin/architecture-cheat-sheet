package lin.louis.clean.usecase.port;

import java.util.Optional;

import lin.louis.clean.entity.Pet;

public interface PetRepository {

	Pet save(Pet pet);

	Optional<Pet> findById(String petId);
}
