package org.mercadolibretipos;

public enum WarrantyTime {
	day("días"), month("meses"), year("años");
	
	private String id;
	
	WarrantyTime(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
}
