package lin.louis.clean.entity;

public class Pet {

	private String petId;

	private String name;

	private String petType;

	private PetStatus status;

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public PetStatus getStatus() {
		return status;
	}

	public void setStatus(PetStatus status) {
		this.status = status;
	}

}
