package lin.louis.layered.domain.simple;

import java.util.Optional;

import lin.louis.layered.domain.OrderService;
import lin.louis.layered.domain.PetService;
import lin.louis.layered.persistence.OrderDAO;
import lin.louis.layered.persistence.entity.Order;
import lin.louis.layered.persistence.entity.PetNotAvailableException;
import lin.louis.layered.persistence.entity.PetNotFoundException;
import lin.louis.layered.persistence.entity.PetStatus;

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
