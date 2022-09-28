package org.Mercadolibre;

public class MLAttribute {
	
	public MLAttribute(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	private String id;
	
	private String name;
	
	private String value_name;
	
	private String value_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue_name() {
		return value_name;
	}

	public void setValue_name(String value_name) {
		this.value_name = value_name;
	}

	public String getValue_id() {
		return value_id;
	}

	public void setValue_id(String value_id) {
		this.value_id = value_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
