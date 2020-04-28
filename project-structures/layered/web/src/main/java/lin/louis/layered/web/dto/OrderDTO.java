package lin.louis.layered.web.dto;

public class OrderDTO {

	private long orderId;

	private PetDTO pet;

	private OrderStatusDTO status;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public PetDTO getPet() {
		return pet;
	}

	public void setPet(PetDTO pet) {
		this.pet = pet;
	}

	public OrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(OrderStatusDTO status) {
		this.status = status;
	}
}
