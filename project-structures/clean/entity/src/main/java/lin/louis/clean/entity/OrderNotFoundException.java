package lin.louis.clean.entity;

public class OrderNotFoundException extends RuntimeException {

	private final String orderId;

	public OrderNotFoundException(String orderId) {
		super("Could not find order with ID " + orderId);
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
}
