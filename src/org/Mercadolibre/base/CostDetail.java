package org.Mercadolibre.base;

import java.math.*;

public class CostDetail {

	private int sender_id;
	
	private BigDecimal amount;

	public int getSender_id() {
		return sender_id;
	}

	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
