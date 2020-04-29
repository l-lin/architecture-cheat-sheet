package lin.louis.clean.adapter.controller.dto;

public class OrderDTO {

	private String orderId;

	private PetDTO pet;

	private OrderStatusDTO status;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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
