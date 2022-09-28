package org.Mercadolibre.base;

import java.math.*;

public class Discount {

	private BigDecimal rate;
	
	private String type;
	
	private BigDecimal promoted_amount;

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPromoted_amount() {
		return promoted_amount;
	}

	public void setPromoted_amount(BigDecimal promoted_amount) {
		this.promoted_amount = promoted_amount;
	}
}
