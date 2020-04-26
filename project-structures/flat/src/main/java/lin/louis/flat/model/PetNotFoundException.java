package lin.louis.flat.model;

public class PetNotFoundException extends PetException {

	public PetNotFoundException(long petId) {
		super(petId, "Could not find pet with ID " + petId);
	}
}
