package lin.louis.layered.web.mapper;

import lin.louis.layered.persistence.entity.Pet;
import lin.louis.layered.persistence.entity.PetStatus;
import lin.louis.layered.web.dto.PetDTO;
import lin.louis.layered.web.dto.PetStatusDTO;

public class PetMapper {

	public Pet map(PetDTO dto) {
		var pet = new Pet();
		pet.setPetId(dto.getPetId());
		pet.setName(dto.getName());
		pet.setPetType(dto.getPetType());
		pet.setStatus(PetStatus.valueOf(dto.getStatus().name()));
		return pet;
	}

	public PetDTO map(Pet pet) {
		var dto = new PetDTO();
		dto.setPetId(pet.getPetId());
		dto.setName(pet.getName());
		dto.setPetType(pet.getPetType());
		dto.setStatus(PetStatusDTO.valueOf(pet.getStatus().name()));
		return dto;
	}
}
