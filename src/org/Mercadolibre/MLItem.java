package org.Mercadolibre;

import java.math.*;
import java.util.*;

import com.google.gson.*;

public class MLItem {

	public static MLItem crear(JsonObject objetoItem, String idItem) throws Exception{
		MLItem itemML = new MLItem(idItem);
		itemML.setPrecio(new BigDecimal(objetoItem.get("price").getAsString()));
		itemML.setStock(new BigDecimal(objetoItem.get("available_quantity").getAsString()));
		itemML.setEstado(MLStatusItem.buscarPor(objetoItem.get("status").getAsString()));
		JsonArray arrayVariaciones = objetoItem.getAsJsonArray("variations");
		if (!arrayVariaciones.isJsonNull()){
//			for(int i=0; i < arrayVariaciones.size(); i++){
//				JsonObject objetoVariacion = arrayVariaciones.get(i).getAsJsonObject();
//				MLVariacionItem variacion = new MLVariacionItem(objetoVariacion.get("id").getAsString());
//				itemML.getVariaciones().add(variacion);
//			}
			for(JsonElement elemnt: arrayVariaciones){
				MLVariacionItem variacion = new Gson().fromJson(elemnt,MLVariacionItem.class);
				itemML.getVariaciones().add(variacion);
			}
			if(itemML.getVariaciones().size() == 1){
				itemML.setVariacionSeleccionada(itemML.getVariaciones().get(0).getId());
			}
		}
		return itemML;
	}
	
	private String idItem; 
	
	private BigDecimal stock; 
	
	private BigDecimal precio; 
	
	private MLItemInformation item;
	
	private MLStatusItem estado = MLStatusItem.ACTIVO;
		
	private List<MLVariacionItem> variaciones = new LinkedList<MLVariacionItem>();
	
	private String variacionSeleccionada;
	
	public MLItem(String _idItem) {
		this.idItem = _idItem;		
	}
	
	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock){
		this.stock = stock;		
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio){		
		this.precio = precio;	
	}

	public List<MLVariacionItem> getVariaciones() {		
		return variaciones;
	}

	public void setVariaciones(List<MLVariacionItem> variaciones) {
		if (variaciones == null){
			this.variaciones = new LinkedList<MLVariacionItem>();
		}
		else{
			this.variaciones = variaciones;
		}
	}

	public MLStatusItem getEstado() {
		return estado;
	}

	public void setEstado(MLStatusItem estado) {
		if (estado != null){
			this.estado = estado;
		}		
	}
	
	public MLItemInformation getItem() {
		if (item == null){
			this.item = new MLItemInformation();
		}
		return item;
	}

	public void setItem(MLItemInformation item) {
		this.item = item;
	}
	
	public JsonObject toJsonObject(){
		JsonObject itemJson = new JsonObject();
		MLItemInformation itemInfo = this.getItem();		
		itemJson.addProperty("title", itemInfo.getTitle());
		itemJson.addProperty("category_id", itemInfo.getCategory_id()); //lista de categorias
		itemJson.addProperty("price", this.getPrecio());
		itemJson.addProperty("currency_id", itemInfo.getCurrency_id().getId());
		itemJson.addProperty("available_quantity", this.getStock());
		itemJson.addProperty("buying_mode", itemInfo.getBuying_mode().getId()); //depende de la categoria
		if(itemInfo.getListing_type_id()!=null) {
			itemJson.addProperty("listing_type_id", itemInfo.getListing_type_id().getId()); //free gold silver
		}
		if(itemInfo.getOfficial_store_id()!=null) {
			itemJson.addProperty("official_store_id", Integer.parseInt(itemInfo.getOfficial_store_id())); //numero de tienda oficial
		}
		//Descripcion del articulo	
		JsonObject description = new JsonObject();
    	description.addProperty("plain_text", itemInfo.getDescription());
    	itemJson.add("description", description);
    	     	
       	JsonArray att1 = new JsonArray();
    	Iterator<MLAttribute> it = itemInfo.getAttributes().iterator();
    	while(it.hasNext()) {
    		MLAttribute attribute = it.next();
    		JsonObject nuevoAtributo = new JsonObject();
    		nuevoAtributo.addProperty("id",attribute.getId());
    		if (attribute.getValue_name() != null){
    			nuevoAtributo.addProperty("value_name", attribute.getValue_name());
    		}
    		else if (attribute.getValue_id() != null){
    			nuevoAtributo.addProperty("value_id", attribute.getValue_id());
        	}    		
    		att1.add(nuevoAtributo);
    	}	
     	JsonObject attributes = new JsonObject();	
     	attributes.addProperty("id", "ITEM_CONDITION");	
    	attributes.addProperty("value_id", itemInfo.getConditions().getId()); 
    	att1.add(attributes);
    	itemJson.add("attributes", att1); 
    	//Garantia
    	JsonObject sale_terms1 = new JsonObject(); 
    	JsonObject sale_terms2 = new JsonObject(); 
    	sale_terms1.addProperty("id", "WARRANTY_TYPE");
    	sale_terms1.addProperty("value_id", itemInfo.getWarranty_type().getId());     	
    	sale_terms2.addProperty("id", "WARRANTY_TIME");
    	sale_terms2.addProperty("value_name", itemInfo.warranty());
    	JsonArray sale = new JsonArray();
    	sale.add(sale_terms1);
    	sale.add(sale_terms2);
    	itemJson.add("sale_terms", sale);		
		
    	//Imagenes
		JsonArray pic = new JsonArray();
    	for(MLPicture mlPicture: itemInfo.getPictures() ){
	    	JsonObject pictures = new JsonObject();    		
	    	if (mlPicture.getId() != null){
	    		pictures.addProperty("id", mlPicture.getId());	    		
	    	}
	    	else if (mlPicture.getUrlSource() != null){
	    		pictures.addProperty("source", mlPicture.getUrlSource());
	    	}
	    	if (!pictures.isJsonNull()){   		    	
	    		pic.add(pictures);
	    	}
    	}
		itemJson.add("pictures", pic);
		return itemJson;    	
	}

	public String getVariacionSeleccionada() {
		return variacionSeleccionada;
	}

	public void setVariacionSeleccionada(String variacionSeleccionada) {
		this.variacionSeleccionada = variacionSeleccionada;
	}
}
