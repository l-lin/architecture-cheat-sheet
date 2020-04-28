package lin.louis.layered.web.mapper;

import lin.louis.layered.persistence.model.Order;
import lin.louis.layered.persistence.model.OrderStatus;
import lin.louis.layered.web.dto.OrderDTO;
import lin.louis.layered.web.dto.OrderStatusDTO;

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
