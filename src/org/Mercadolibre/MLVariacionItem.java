package org.Mercadolibre;

public class MLVariacionItem {
	
	private String id;
	
	private String available_quantity;
	
	private String price;

	public String getAvailable_quantity() {
		return available_quantity;
	}

	public void setAvailable_quantity(String available_quantity) {
		this.available_quantity = available_quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public MLVariacionItem(String id){
		this.setId(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
}
