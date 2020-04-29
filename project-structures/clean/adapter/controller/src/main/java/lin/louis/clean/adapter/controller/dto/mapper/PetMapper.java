package lin.louis.clean.adapter.controller.dto.mapper;

import lin.louis.clean.adapter.controller.dto.PetDTO;
import lin.louis.clean.adapter.controller.dto.PetStatusDTO;
import lin.louis.clean.entity.Pet;
import lin.louis.clean.entity.PetStatus;

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
