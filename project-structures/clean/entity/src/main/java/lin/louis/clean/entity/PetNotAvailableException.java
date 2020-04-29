package lin.louis.clean.entity;

public class PetNotAvailableException extends PetException {

	public PetNotAvailableException(String petId) {
		super(petId, "Pet with ID " + petId + " is not available");
	}
}
