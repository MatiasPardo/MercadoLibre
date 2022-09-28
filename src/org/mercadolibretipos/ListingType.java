package org.mercadolibretipos;

public enum ListingType {
	OroPremiumFull("gold_pro"), 
	OroPremium("gold_premium"),
	OroProfesional("gold_special"),
	Oro("gold"),
	Plata("silver"),
	Bronce("bronze"), 
	Gratuita("free");
	
	private String id;
	
	ListingType(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public static ListingType valueId(String id) throws Exception{
		for(ListingType value: values()){
			if (value.getId().equals(id)){
				return value;
			}
		}
		throw new Exception("No se encontró ListingType " + id);
	}
	
}
