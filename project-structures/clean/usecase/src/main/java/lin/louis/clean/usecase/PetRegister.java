package lin.louis.clean.usecase;

import lin.louis.clean.entity.Pet;
import lin.louis.clean.usecase.port.IdGenerator;
import lin.louis.clean.usecase.port.PetRepository;

public class PetRegister {

	private final IdGenerator idGenerator;

	private final PetRepository petRepository;

	public PetRegister(IdGenerator idGenerator, PetRepository petRepository) {
		this.idGenerator = idGenerator;
		this.petRepository = petRepository;
	}

	public Pet register(Pet pet) {
		var petToSave = new Pet();
		petToSave.setPetId(idGenerator.generate());
		petToSave.setName(pet.getName());
		petToSave.setPetType(pet.getPetType());
		petToSave.setStatus(pet.getStatus());
		return petRepository.save(petToSave);
	}
}
