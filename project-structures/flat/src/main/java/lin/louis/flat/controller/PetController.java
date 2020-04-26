package lin.louis.flat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.flat.model.Order;
import lin.louis.flat.model.Pet;
import lin.louis.flat.model.PetNotFoundException;
import lin.louis.flat.service.OrderService;
import lin.louis.flat.service.PetService;

@RestController
@RequestMapping(path = "/pets")
public class PetController {

	private final PetService petService;

	private final OrderService orderService;

	public PetController(PetService petService, OrderService orderService) {
		this.petService = petService;
		this.orderService = orderService;
	}

	@GetMapping(path = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pet findById(@PathVariable long petId) {
		return petService.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Pet save(@RequestBody Pet pet) {
		return petService.save(pet);
	}

	@PostMapping(path = "/{petId}/orders",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Order order(@PathVariable long petId, @RequestBody Order order) {
		return orderService.save(petId, order);
	}
}
