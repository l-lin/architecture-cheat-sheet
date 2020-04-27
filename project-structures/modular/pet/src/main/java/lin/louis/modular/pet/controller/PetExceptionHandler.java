package lin.louis.modular.pet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lin.louis.modular.pet.model.PetNotAvailableException;
import lin.louis.modular.pet.model.PetNotFoundException;

@ControllerAdvice
public class PetExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = PetNotFoundException.class)
	public ResponseEntity<Object> notFound(Exception ex) {
		logger.debug(ex.getMessage());
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = PetNotAvailableException.class)
	public ResponseEntity<Object> notAvailable(PetNotAvailableException ex) {
		logger.info(ex.getMessage());
		return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
	}
}
