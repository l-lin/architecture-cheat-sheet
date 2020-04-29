package lin.louis.clean.application.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.clean.adapter.controller.PetController;
import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.PetDTO;

@RestController
@RequestMapping(path = "/pets")
public class SpringPetController {

	private final PetController petController;

	public SpringPetController(PetController petController) {
		this.petController = petController;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PetDTO register(@RequestBody PetDTO pet) {
		return petController.register(pet);
	}

	@GetMapping(path = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PetDTO findById(@PathVariable String petId) {
		return petController.findById(petId);
	}

	@PostMapping(path = "/{petId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO order(@PathVariable String petId) {
		return petController.order(petId);
	}
}
