package org.mercadolibretipos;

import java.util.*;

public class Billing_info {
	
	private String doc_type;
	
	private String doc_number;
	
	private List<Additional_info> additional_info;
	
	public List<Additional_info> getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(List<Additional_info> additional_info) {
		this.additional_info = additional_info;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getDoc_number() {
		return doc_number;
	}

	public void setDoc_number(String doc_number) {
		this.doc_number = doc_number;
	}
	
}
