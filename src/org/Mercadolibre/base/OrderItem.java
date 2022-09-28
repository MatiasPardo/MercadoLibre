package org.Mercadolibre.base;

import java.math.*;

public class OrderItem {
	
	private Item item;
	
	private BigDecimal quantity;
	
	private BigDecimal unit_price;
	
	private BigDecimal full_unit_price;
	
	private String currency_id;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public BigDecimal getFull_unit_price() {
		return full_unit_price;
	}

	public void setFull_unit_price(BigDecimal full_unit_price) {
		this.full_unit_price = full_unit_price;
	}

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}
	
}
