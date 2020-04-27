package lin.louis.modular.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lin.louis.modular.order.model.OrderNotFoundException;

@ControllerAdvice
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { OrderNotFoundException.class })
	public ResponseEntity<Object> notFound(Exception ex) {
		logger.debug(ex.getMessage());
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}
}
