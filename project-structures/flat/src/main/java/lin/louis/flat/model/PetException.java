package lin.louis.flat.model;

public class PetException extends RuntimeException {
	private final long petId;

	public PetException(long petId, String message) {
		super(message);
		this.petId = petId;
	}

	public long getPetId() {
		return petId;
	}
}
