package lin.louis.layered.persistence.model;

public class PetNotAvailableException extends PetException {

	public PetNotAvailableException(long petId) {
		super(petId, "Pet with ID " + petId + " is not available");
	}
}
