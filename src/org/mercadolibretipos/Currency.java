package org.mercadolibretipos;

public enum Currency {
	Argentina("ARS");
	
	private String id;
	
	Currency(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public static Currency valueId(String id) throws Exception{
		for(Currency currency: values()){
			if (currency.getId().equals(id)){
				return currency;
			}
		}
		throw new Exception("No se encontró Currency " + id);
	}
}
