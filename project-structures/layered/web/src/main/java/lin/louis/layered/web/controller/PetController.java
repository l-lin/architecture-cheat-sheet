package lin.louis.layered.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.layered.domain.OrderService;
import lin.louis.layered.domain.PetService;
import lin.louis.layered.persistence.entity.PetNotFoundException;
import lin.louis.layered.web.dto.OrderDTO;
import lin.louis.layered.web.dto.PetDTO;
import lin.louis.layered.web.dto.mapper.OrderMapper;
import lin.louis.layered.web.dto.mapper.PetMapper;

@RestController
@RequestMapping(path = "/pets")
public class PetController {

	private final PetService petService;

	private final OrderService orderService;

	private final PetMapper petMapper;

	private final OrderMapper orderMapper;

	public PetController(
			PetService petService,
			OrderService orderService,
			PetMapper petMapper,
			OrderMapper orderMapper
	) {
		this.petService = petService;
		this.orderService = orderService;
		this.petMapper = petMapper;
		this.orderMapper = orderMapper;
	}

	@GetMapping(path = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PetDTO findById(@PathVariable long petId) {
		var pet = petService.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
		return petMapper.map(pet);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PetDTO save(@RequestBody PetDTO dto) {
		var pet = petService.save(petMapper.map(dto));
		return petMapper.map(pet);
	}

	@PostMapping(path = "/{petId}/orders",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO order(@PathVariable long petId, @RequestBody OrderDTO dto) {
		var order = orderService.save(petId, orderMapper.map(dto));
		return orderMapper.map(order);
	}
}
