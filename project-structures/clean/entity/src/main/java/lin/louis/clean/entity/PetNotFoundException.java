package lin.louis.clean.entity;

public class PetNotFoundException extends PetException {

	public PetNotFoundException(String petId) {
		super(petId, "Could not find pet with ID " + petId);
	}
}
