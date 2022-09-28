package org.Mercadolibre;

public enum MLStatusItem {
	
	ACTIVO("active"), PAUSADO("paused"), CERRADO("closed");
	
	private String status;
	
	MLStatusItem(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	public static MLStatusItem buscarPor(String status) throws Exception{
		if (status != null){
			MLStatusItem[] lista = MLStatusItem.values();
			for (int i=0; i < lista.length; i++){
				if (lista[i].getStatus().equals(status)){
					return lista[i];
				}
			}
		}	
		throw new Exception("no esta definido el status del item: " + status);
	}
}
