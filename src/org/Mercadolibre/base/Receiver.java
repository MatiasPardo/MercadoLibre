package org.Mercadolibre.base;

import java.math.*;
import java.util.*;

public class Receiver {

	private int user_id;
	
	private BigDecimal cost;
	
	private BigDecimal compensation;
	
	private BigDecimal sabe;
	
	private Collection<Discount> discounts;
	
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

	public BigDecimal getSabe() {
		return sabe;
	}

	public void setSabe(BigDecimal sabe) {
		this.sabe = sabe;
	}

	public Collection<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Collection<Discount> discounts) {
		this.discounts = discounts;
	}

	public Collection<CostDetail> getCost_details() {
		return cost_details;
	}

	public void setCost_details(Collection<CostDetail> cost_details) {
		this.cost_details = cost_details;
	}

	private Collection<CostDetail> cost_details;
	
}
