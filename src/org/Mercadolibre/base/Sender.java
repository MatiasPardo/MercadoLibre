package org.Mercadolibre.base;

import java.math.*;
import java.util.*;

public class Sender {

	private int user_id;
	
	private BigDecimal cost;
	
	private BigDecimal compensation;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getCompensation() {
		return compensation;
	}

	public void setCompensation(BigDecimal compensation) {
		this.compensation = compensation;
	}

	public Collection<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Collection<Discount> discounts) {
		this.discounts = discounts;
	}

	private Collection<Discount> discounts;
}
