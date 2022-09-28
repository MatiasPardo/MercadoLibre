package org.Mercadolibre;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import org.mercadolibretipos.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MLItemInformation {
	
	public static MLItemInformation crear(JsonObject json) throws Exception{
		try{
			MLItemInformation itemInformation = new MLItemInformation();
			itemInformation.setTitle(json.get("title").getAsString());
			itemInformation.setOfficial_store_id(json.get("official_store_id").getAsString());
			itemInformation.setCategory_id(json.get("category_id").getAsString());
			itemInformation.setCurrency_id(Currency.valueId(json.get("currency_id").getAsString()));
			itemInformation.setBuying_mode(BuyingMode.valueId(json.get("buying_mode").getAsString()));
			itemInformation.setListing_type_id(ListingType.valueId(json.get("listing_type_id").getAsString()));
			JsonArray array = json.getAsJsonArray("attributes");
			if (array != null){
				Iterator<JsonElement> it = array.iterator();				
				while(it.hasNext()){
					JsonElement jsonAttribute = it.next();
					if (jsonAttribute.isJsonObject()){
						JsonObject object = jsonAttribute.getAsJsonObject();
						if (!object.get("id").getAsString().equals("ITEM_CONDITION")){
							String nameAttribute = "";
							if (!object.get("name").isJsonNull()){
								nameAttribute = object.get("name").getAsString();
							}
							MLAttribute attribute = new MLAttribute(object.get("id").getAsString(), nameAttribute);							
							if (!object.get("value_name").isJsonNull()){
								attribute.setValue_name(object.get("value_name").getAsString());
							}
							else if (!object.get("value_id").isJsonNull()){
								attribute.setValue_id(object.get("value_id").getAsString());
							}
							else{
								throw new Exception("El atributo de id " + attribute.getId() + " no tiene value_name/value_id");
							}
							itemInformation.getAttributes().add(attribute);
						}
						else{
							itemInformation.setConditions(ConditionItem.valueId(object.get("value_id").getAsString()));
						}						
					}
				}
			}
			
			array = json.getAsJsonArray("pictures");
			if (array != null){
				Iterator<JsonElement> it = array.iterator();				
				while(it.hasNext()){
					JsonElement attribute = it.next();
					if (attribute.isJsonObject()){
						MLPicture picture = new MLPicture();
						picture.setId(attribute.getAsJsonObject().get("id").getAsString());
						itemInformation.getPictures().add(picture);						
					}
				}
			}
			
			itemInformation.assignWarranty(json.get("warranty").getAsString());
			return itemInformation;
		}
		catch(Exception e){
			throw new Exception("Error al buscar item information: " + e.toString());
		}
	}
	
	private String title;
	
	private String official_store_id;
	
	private String category_id;
	
	private Currency currency_id = Currency.Argentina;

	private BuyingMode buying_mode = BuyingMode.Ahora;
	
	private ListingType listing_type_id;
	
	private String description;
	
	private ConditionItem conditions = ConditionItem.Nuevo;
	
	private WarrantyType warranty_type;
	
	private WarrantyTime warranty_time;
	
	private Integer warranty_count;
	
	private List<MLPicture> pictures;
		
	private List<MLAttribute> attributes;
	

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

	public Currency getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Currency currency_id) {
		this.currency_id = currency_id;
	}

	public BuyingMode getBuying_mode() {
		return buying_mode;
	}

	public void setBuying_mode(BuyingMode buying_mode) {
		this.buying_mode = buying_mode;
	}

	public ListingType getListing_type_id() {
		return listing_type_id;
	}

	public void setListing_type_id(ListingType listing_type_id) {
		this.listing_type_id = listing_type_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ConditionItem getConditions() {
		return conditions;
	}

	public void setConditions(ConditionItem conditions) {
		this.conditions = conditions;
	}

	public WarrantyType getWarranty_type() {
		return warranty_type;
	}

	public void setWarranty_type(WarrantyType warranty_type) {
		this.warranty_type = warranty_type;
	}

	public WarrantyTime getWarranty_time() {
		return warranty_time;
	}

	public void setWarranty_time(WarrantyTime warranty_time) {
		this.warranty_time = warranty_time;
	}

	public Integer getWarranty_count() {
		return warranty_count;
	}

	public void setWarranty_count(Integer warranty_count) {
		this.warranty_count = warranty_count;
	}

	public String warranty(){
		String str = null;
		if ((this.getWarranty_time() != null) && (this.getWarranty_count() != null)){
			str = this.getWarranty_count().toString() + " " + this.getWarranty_time().getId();
		}
		return str;
	}
	
	public List<MLPicture> getPictures() {
		if (pictures == null) this.pictures = new LinkedList<MLPicture>();
		return pictures;
	}

	public String getOfficial_store_id() {
		return official_store_id;
	}

	public void setOfficial_store_id(String official_store_id) {
		this.official_store_id = official_store_id;
	}

	public List<MLAttribute> getAttributes() {
		if (attributes == null){
			attributes = new LinkedList<MLAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<MLAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void assignWarranty(String warranty) {
		// "Garantía del vendedor: 90 días"
		String w = warranty.toLowerCase();
		if (w.indexOf("vendedor") > 0){
			this.setWarranty_type(WarrantyType.Vendedor);
		}
		else{
			this.setWarranty_type(WarrantyType.Fabrica);
		}
					
		String[] split = w.split("\\D+");
		if ((split != null) && (split.length > 1)){
			this.setWarranty_count(Integer.decode(split[1]));
		}
		
		split = w.split("\\d+");
		if ((split != null) && (split.length > 1)){
			String periodo = split[1];
			if ((periodo.indexOf("meses") > 0) || (periodo.indexOf("mes") > 0)){
				this.setWarranty_time(WarrantyTime.month);
			}
			else if (periodo.indexOf("d") > 0){
				this.setWarranty_time(WarrantyTime.day);
			}
			else if (periodo.indexOf("a") > 0){
				this.setWarranty_time(WarrantyTime.year);
			}
		}		
	}
}

