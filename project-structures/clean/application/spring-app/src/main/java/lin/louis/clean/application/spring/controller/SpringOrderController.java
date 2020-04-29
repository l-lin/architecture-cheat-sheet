package lin.louis.clean.application.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.clean.adapter.controller.OrderController;
import lin.louis.clean.adapter.controller.dto.OrderDTO;

@RestController
@RequestMapping(path = "/orders")
public class SpringOrderController {

	private final OrderController orderController;

	public SpringOrderController(OrderController orderController) {
		this.orderController = orderController;
	}

	@GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDTO findById(@PathVariable String orderId) {
		return orderController.findById(orderId);
	}
}
