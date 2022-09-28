package org.mercadolibretipos;

public enum WarrantyType {
	Fabrica("2230279"), Vendedor("2230280");
	
	private String id;
	
	WarrantyType(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
}
