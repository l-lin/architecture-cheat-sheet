package lin.louis.flat.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.flat.model.Order;
import lin.louis.flat.model.OrderNotFoundException;
import lin.louis.flat.service.OrderService;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Order findById(@PathVariable long orderId) {
		return orderService.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
	}
}
