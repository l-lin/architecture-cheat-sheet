package lin.louis.clean.adapter.controller;

import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.PetDTO;
import lin.louis.clean.adapter.controller.dto.mapper.OrderMapper;
import lin.louis.clean.adapter.controller.dto.mapper.PetMapper;
import lin.louis.clean.usecase.PetFinder;
import lin.louis.clean.usecase.PetOrderer;
import lin.louis.clean.usecase.PetRegister;

public class PetController {

	private final PetRegister petRegister;

	private final PetFinder petFinder;

	private final PetOrderer petOrderer;

	private final PetMapper petMapper;

	private final OrderMapper orderMapper;

	public PetController(
			PetRegister petRegister,
			PetFinder petFinder,
			PetOrderer petOrderer,
			PetMapper petMapper,
			OrderMapper orderMapper
	) {
		this.petRegister = petRegister;
		this.petFinder = petFinder;
		this.petOrderer = petOrderer;
		this.petMapper = petMapper;
		this.orderMapper = orderMapper;
	}

	public PetDTO register(PetDTO pet) {
		var petDTO = petMapper.map(pet);
		return petMapper.map(petRegister.register(petDTO));
	}

	public PetDTO findById(String petId) {
		return petMapper.map(petFinder.findById(petId));
	}

	public OrderDTO order(String petId) {
		return orderMapper.map(petOrderer.order(petId));
	}
}
