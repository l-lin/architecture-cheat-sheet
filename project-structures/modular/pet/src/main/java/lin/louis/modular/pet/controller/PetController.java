package lin.louis.modular.pet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.modular.pet.model.Pet;
import lin.louis.modular.pet.model.PetNotFoundException;
import lin.louis.modular.pet.service.PetService;

@RestController
@RequestMapping(path = "/pets")
public class PetController {

	private final PetService petService;

	public PetController(PetService petService) {
		this.petService = petService;
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
}
