package lin.louis.modular.order.model;

public class OrderNotFoundException extends RuntimeException {

	private final long orderId;

	public OrderNotFoundException(long orderId) {
		super("Could not find order with ID " + orderId);
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}
}
