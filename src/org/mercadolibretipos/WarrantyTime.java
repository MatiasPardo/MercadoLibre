package org.mercadolibretipos;

public enum WarrantyTime {
	day("d�as"), month("meses"), year("a�os");
	
	private String id;
	
	WarrantyTime(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
}
