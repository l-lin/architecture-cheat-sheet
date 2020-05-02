package lin.louis.modular.order.service.impl;

import java.util.Optional;

import lin.louis.modular.order.dao.OrderDAO;
import lin.louis.modular.order.model.Order;
import lin.louis.modular.order.service.OrderService;
import lin.louis.modular.pet.model.PetNotAvailableException;
import lin.louis.modular.pet.model.PetNotFoundException;
import lin.louis.modular.pet.model.PetStatus;
import lin.louis.modular.pet.service.PetService;

/**
 * A simple implementation that encapsulates a {@link OrderDAO}, so we can easily switch the persistence layer without
 * pain, for example if we want to use an external database, we don't need to change the service, which is the purpose
 * of the strategy pattern.
 */
public class SimpleOrderService implements OrderService {

	private final PetService petService;

	private final OrderDAO orderDAO;

	public SimpleOrderService(PetService petService, OrderDAO orderDAO) {
		this.petService = petService;
		this.orderDAO = orderDAO;
	}

	@Override
	public Order save(long petId, Order order) {
		var pet = petService.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
		if (PetStatus.AVAILABLE != pet.getStatus()) {
			throw new PetNotAvailableException(petId);
		}
		order.setPet(pet);
		var orderId = orderDAO.save(order);
		order.setOrderId(orderId);
		return order;
	}

	@Override
	public Optional<Order> findById(long orderId) {
		return orderDAO.findById(orderId);
	}
}
