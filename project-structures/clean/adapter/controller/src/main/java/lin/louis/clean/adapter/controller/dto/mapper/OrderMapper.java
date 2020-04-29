package lin.louis.clean.adapter.controller.dto.mapper;

import lin.louis.clean.adapter.controller.dto.OrderDTO;
import lin.louis.clean.adapter.controller.dto.OrderStatusDTO;
import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderStatus;

public class OrderMapper {

	private final PetMapper petMapper;

	public OrderMapper(PetMapper petMapper) {
		this.petMapper = petMapper;
	}

	public Order map(OrderDTO dto) {
		var order = new Order();
		order.setOrderId(dto.getOrderId());
		if (dto.getPet() != null) {
			order.setPet(petMapper.map(dto.getPet()));
		}
		order.setStatus(OrderStatus.valueOf(dto.getStatus().name()));
		return order;
	}

	public OrderDTO map(Order order) {
		var dto = new OrderDTO();
		dto.setOrderId(order.getOrderId());
		if (order.getPet() != null) {
			dto.setPet(petMapper.map(order.getPet()));
		}
		dto.setStatus(OrderStatusDTO.valueOf(order.getStatus().name()));
		return dto;
	}
}
