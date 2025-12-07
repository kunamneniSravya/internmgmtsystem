package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;

public class StipendDto {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotBlank(message = "User name is required")
	private String userName;

	@NotBlank(message = "Payment mode is required")
	private String paymentMode;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "5000.0", message = "Amount must be greater than 5000")
	private Double amount;

	public StipendDto() {
	}

	public StipendDto(@NotNull(message = "User ID is required") Long userId,
			@NotBlank(message = "User name is required") String userName,
			@NotBlank(message = "Payment mode is required") String paymentMode,
			@NotNull(message = "Amount is required") @DecimalMin(value = "1.0", message = "Amount must be greater than 0") Double amount) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.paymentMode = paymentMode;
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	// getters & setters
}
