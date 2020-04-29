package lin.louis.clean.entity;

public class PetException extends RuntimeException {

	private final String petId;

	public PetException(String petId, String message) {
		super(message);
		this.petId = petId;
	}

	public String getPetId() {
		return petId;
	}
}
