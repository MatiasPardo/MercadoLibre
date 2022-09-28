package org.Mercadolibre.base;

import java.math.*;

public class Payment {

	private int id;
	
	private int order_id;
	
	private int payer_id;
	
	private int card_id;
	
	private String site_id;
	
	private String reason;
	
	private String payment_method_id;
	
	private String currency_id;
	
	private int installments;
	
	private String issuer_id;
	
	private BigDecimal total_paid_amount;
	
	private BigDecimal installment_amount;
	
	private BigDecimal shipping_cost;
	
	public BigDecimal getTotal_paid_amount() {
		return total_paid_amount;
	}
	public void setTotal_paid_amount(BigDecimal total_paid_amount) {
		this.total_paid_amount = total_paid_amount;
	}
	public BigDecimal getInstallment_amount() {
		return installment_amount;
	}
	public void setInstallment_amount(BigDecimal installment_amount) {
		this.installment_amount = installment_amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(int payer_id) {
		this.payer_id = payer_id;
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPayment_method_id() {
		return payment_method_id;
	}
	public void setPayment_method_id(String payment_method_id) {
		this.payment_method_id = payment_method_id;
	}
	public String getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}
	public int getInstallments() {
		return installments;
	}
	public void setInstallments(int installments) {
		this.installments = installments;
	}
	public String getIssuer_id() {
		return issuer_id;
	}
	public void setIssuer_id(String issuer_id) {
		this.issuer_id = issuer_id;
	}
	public BigDecimal getShipping_cost() {
		return shipping_cost;
	}
	public void setShipping_cost(BigDecimal shipping_cost) {
		this.shipping_cost = shipping_cost;
	}
	
    
}
