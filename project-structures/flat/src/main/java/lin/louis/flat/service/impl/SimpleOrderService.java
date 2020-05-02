package lin.louis.flat.service.impl;

import java.util.Optional;

import lin.louis.flat.dao.OrderDAO;
import lin.louis.flat.model.Order;
import lin.louis.flat.model.PetNotAvailableException;
import lin.louis.flat.model.PetNotFoundException;
import lin.louis.flat.model.PetStatus;
import lin.louis.flat.service.OrderService;
import lin.louis.flat.service.PetService;

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
