package org.Mercadolibre;

import java.math.*;


public class MLItemPedido {

	private String IdItem;
	
	private BigDecimal Cantidad;
	
	private Double Precio;
	
	private String Titulo;
	
	private String CategoriaId;
	
	private Boolean Nuevo;
	
	private Boolean Garantia;
	
	private String idVariante;
	
	private Long packOrder;
	
	public Long getPackOrder() {
		return packOrder;
	}

	public void setPackOrder(Long packOrder) {
		this.packOrder = packOrder;
	}

	public String getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(String idVariante) {
		this.idVariante = idVariante;
	}

	public String getIdItem() {
		return IdItem;
	}

	public void setIdItem(String idItem) {
		IdItem = idItem;
	}

	public BigDecimal getCantidad() {
		return Cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		Cantidad = cantidad;
	}

	public Double getPrecio() {
		return Precio;
	}

	public void setPrecio(Double precio) {
		Precio = precio;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public String getCategoriaId() {
		return CategoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		CategoriaId = categoriaId;
	}

	public Boolean getNuevo() {
		return Nuevo;
	}

	public void setNuevo(Boolean nuevo) {
		Nuevo = nuevo;
	}

	public Boolean getGarantia() {
		return Garantia;
	}

	public void setGarantia(Boolean garantia) {
		Garantia = garantia;
	}	
	
}
