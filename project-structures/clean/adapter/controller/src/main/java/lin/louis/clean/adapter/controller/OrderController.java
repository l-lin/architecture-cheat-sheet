package lin.louis.clean.adapter.controller;

import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.mapper.OrderMapper;
import lin.louis.clean.usecase.OrderFinder;

public class OrderController {

	private final OrderFinder orderFinder;

	private final OrderMapper orderMapper;

	public OrderController(OrderFinder orderFinder, OrderMapper orderMapper) {
		this.orderFinder = orderFinder;
		this.orderMapper = orderMapper;
	}

	public OrderDTO findById(String orderId) {
		return orderMapper.map(orderFinder.findById(orderId));
	}
}
