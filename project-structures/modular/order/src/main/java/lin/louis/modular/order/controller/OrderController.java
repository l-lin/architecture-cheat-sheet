package lin.louis.modular.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.modular.order.model.Order;
import lin.louis.modular.order.model.OrderNotFoundException;
import lin.louis.modular.order.service.OrderService;

@RestController
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(path = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Order findById(@PathVariable long orderId) {
		return orderService.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
	}

	@PostMapping(path = "/pets/{petId}/orders",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Order order(@PathVariable long petId, @RequestBody Order order) {
		return orderService.save(petId, order);
	}
}
