package lin.louis.layered.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lin.louis.layered.domain.OrderService;
import lin.louis.layered.persistence.entity.OrderNotFoundException;
import lin.louis.layered.web.dto.OrderDTO;
import lin.louis.layered.web.dto.mapper.OrderMapper;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

	private final OrderService orderService;

	private final OrderMapper orderMapper;

	public OrderController(OrderService orderService, OrderMapper orderMapper) {
		this.orderService = orderService;
		this.orderMapper = orderMapper;
	}

	@GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDTO findById(@PathVariable long orderId) {
		var order = orderService.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		return orderMapper.map(order);
	}
}
