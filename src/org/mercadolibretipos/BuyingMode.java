package org.mercadolibretipos;

public enum BuyingMode {
	Ahora("buy_it_now");
	
	private String id;
	
	BuyingMode(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public static BuyingMode valueId(String id) throws Exception{
		for(BuyingMode value: values()){
			if (value.getId().equals(id)){
				return value;
			}
		}
		throw new Exception("No se encontró BuyingMode " + id);
	}
	
}
