package org.Mercadolibre.base;

import java.math.*;
import java.util.*;


public class Order {
	
	private String id;
	
	private String date_created;
	
	private String date_closed;
		
	private String pack_id;

	private Boolean fulfilled;
	
	private BigDecimal total_amount;
	
	private BigDecimal paid_amount;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getDate_closed() {
		return date_closed;
	}

	public void setDate_closed(String date_closed) {
		this.date_closed = date_closed;
	}

	public String getPack_id() {
		return pack_id;
	}

	public void setPack_id(String pack_id) {
		this.pack_id = pack_id;
	}

	public Boolean getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(Boolean fulfilled) {
		this.fulfilled = fulfilled;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(BigDecimal paid_amount) {
		this.paid_amount = paid_amount;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Collection<OrderItem> getOrder_items() {
		return order_items;
	}

	public void setOrder_items(Collection<OrderItem> order_items) {
		this.order_items = order_items;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Value getShipping() {
		return shipping;
	}

	public void setShipping(Value shipping) {
		this.shipping = shipping;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_detail() {
		return status_detail;
	}

	public void setStatus_detail(String status_detail) {
		this.status_detail = status_detail;
	}

	public Value getSeller() {
		return seller;
	}

	public void setSeller(Value seller) {
		this.seller = seller;
	}

	public Value getBuyer() {
		return buyer;
	}

	public void setBuyer(Value buyer) {
		this.buyer = buyer;
	}

	private Coupon coupon;
	
	private Collection<OrderItem> order_items;
	
	private List<Payment> payments;
	
	private Value shipping;
	
	private String status;
	
	private String status_detail;
	
	private Value seller;
	
	private Value buyer;
	
	
}
