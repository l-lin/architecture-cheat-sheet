package lin.louis.clean.adapter.controller.dto;

public class PetDTO {

	private String petId;

	private String name;

	private String petType;

	private PetStatusDTO status;

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

	public PetStatusDTO getStatus() {
		return status;
	}

	public void setStatus(PetStatusDTO status) {
		this.status = status;
	}

}
