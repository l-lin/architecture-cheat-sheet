package lin.louis.layered.persistence.model;

public class PetNotFoundException extends PetException {

	public PetNotFoundException(long petId) {
		super(petId, "Could not find pet with ID " + petId);
	}
}
