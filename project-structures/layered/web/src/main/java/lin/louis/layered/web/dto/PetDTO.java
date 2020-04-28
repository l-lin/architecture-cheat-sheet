package lin.louis.layered.web.dto;

public class PetDTO {

	private long petId;

	private String name;

	private String petType;

	private PetStatusDTO status;

	public long getPetId() {
		return petId;
	}

	public void setPetId(long petId) {
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
