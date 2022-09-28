package org.Mercadolibre.base;

public class Item {
	
	private String id;
	
	private String title;
	
	private String category_id;
	
	private String variation_id;
	
	private VariationAttributes variation_attributes;
	
	private String warranty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getVariation_id() {
		return variation_id;
	}

	public void setVariation_id(String variation_id) {
		this.variation_id = variation_id;
	}

	public VariationAttributes getVariation_attributes() {
		return variation_attributes;
	}

	public void setVariation_attributes(VariationAttributes variation_attributes) {
		this.variation_attributes = variation_attributes;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSeller_sku() {
		return seller_sku;
	}

	public void setSeller_sku(String seller_sku) {
		this.seller_sku = seller_sku;
	}

	private String condition;
	
	private String seller_sku;
}
