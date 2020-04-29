package lin.louis.clean.usecase;

import lin.louis.clean.entity.Order;
import lin.louis.clean.entity.OrderStatus;
import lin.louis.clean.entity.PetNotAvailableException;
import lin.louis.clean.entity.PetNotFoundException;
import lin.louis.clean.entity.PetStatus;
import lin.louis.clean.usecase.port.IdGenerator;
import lin.louis.clean.usecase.port.OrderRepository;
import lin.louis.clean.usecase.port.PetRepository;

public class PetOrderer {

	private final IdGenerator idGenerator;

	private final PetRepository petRepository;

	private final OrderRepository orderRepository;

	public PetOrderer(
			IdGenerator idGenerator,
			PetRepository petRepository,
			OrderRepository orderRepository
	) {
		this.idGenerator = idGenerator;
		this.petRepository = petRepository;
		this.orderRepository = orderRepository;
	}

	public Order order(String petId) {
		var pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
		if (PetStatus.AVAILABLE != pet.getStatus()) {
			throw new PetNotAvailableException(petId);
		}
		var order = new Order();
		order.setOrderId(idGenerator.generate());
		order.setPet(pet);
		order.setStatus(OrderStatus.PLACED);
		return orderRepository.save(order);
	}
}
