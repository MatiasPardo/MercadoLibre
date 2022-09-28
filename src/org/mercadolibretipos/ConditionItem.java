package org.mercadolibretipos;

public enum ConditionItem {
	Nuevo("2230284"), Usado("2230581"), Reacondicionado("2230582");
	
	ConditionItem(String id){
		this.id = id;
	}
	
	private String id;
	
	
	public String getId(){
		return this.id;
	}
	
	public static ConditionItem valueId(String id) throws Exception{
		for(ConditionItem value: values()){
			if (value.getId().equals(id)){
				return value;
			}
		}
		throw new Exception("No se encontró ConditionItem " + id);
	}
}
