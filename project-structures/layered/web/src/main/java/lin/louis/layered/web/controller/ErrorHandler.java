package lin.louis.layered.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lin.louis.layered.persistence.entity.OrderNotFoundException;
import lin.louis.layered.persistence.entity.PetNotAvailableException;
import lin.louis.layered.persistence.entity.PetNotFoundException;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { PetNotFoundException.class, OrderNotFoundException.class })
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
