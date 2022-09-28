package org.Mercadolibre.base;

import java.math.*;
import java.util.*;

public class Shipment {
	
	private BigDecimal gross_amount;
	
	private Receiver receiver;
	
	private Collection<Sender> senders;

	public BigDecimal getGross_amount() {
		return gross_amount;
	}

	public void setGross_amount(BigDecimal gross_amount) {
		this.gross_amount = gross_amount;
	}

	public Collection<Sender> getSenders() {
		return senders;
	}

	public void setSenders(Collection<Sender> senders) {
		this.senders = senders;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

}
