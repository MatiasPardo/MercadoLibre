package org.Mercadolibre;

import java.math.*;
import java.util.*;

public class MLPedido {

	private String idUsuarioComprador;
	
	private String nombreUsuarioCompra;
	
	private Date fecha;
	
	private Date fechaCierre;
	
	private BigDecimal precioTotal;
	
	private Collection<MLItemPedido> items;
	
	private String nombreArchivo;
	
	private long numeroPedido;
	
	private Date fechaCreacion;	

	private String nombreComprador;
	
	private String apellidoComprador;
	
	private String tipoDocumentoComprador;
	
	private String numeroDocumentoComprador;
			
	private String emailComprador;
	
	private String shipping;
	
	private Boolean cancelada = Boolean.FALSE;
	
	private String packId;
	
	private BigDecimal shipping_cost;
	
	private BigDecimal coupon_amount;
	
	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public long getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getIdUsuarioComprador() {
		return idUsuarioComprador;
	}

	public void setIdUsuarioComprador(String idUsuarioComprador) {
		this.idUsuarioComprador = idUsuarioComprador;
	}

	public String getNombreUsuarioCompra() {
		return nombreUsuarioCompra;
	}

	public void setNombreUsuarioCompra(String nombreUsuarioCompra) {
		this.nombreUsuarioCompra = nombreUsuarioCompra;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Collection<MLItemPedido> getItems() {
		return items;
	}

	public void setItems(Collection<MLItemPedido> items) {
		this.items = items;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public long getNumeroDePedido() {
		return numeroPedido;
	}

	public void setNumeroDePedido(long numeroDePedido) {
		this.numeroPedido = numeroDePedido;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public String getApellidoComprador() {
		return apellidoComprador;
	}

	public void setApellidoComprador(String apellidoComprador) {
		this.apellidoComprador = apellidoComprador;
	}

	public String getTipoDocumentoComprador() {
		return tipoDocumentoComprador;
	}

	public void setTipoDocumentoComprador(String tipoDocumentoComprador) {
		this.tipoDocumentoComprador = tipoDocumentoComprador;
	}

	public String getNumeroDocumentoComprador() {
		return numeroDocumentoComprador;
	}

	public void setNumeroDocumentoComprador(String numeroDocumentoComprador) {
		this.numeroDocumentoComprador = numeroDocumentoComprador;
	}

	public String getEmailComprador() {
		return emailComprador;
	}

	public void setEmailComprador(String emailComprador) {
		this.emailComprador = emailComprador;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public BigDecimal getShipping_cost() {
		return shipping_cost != null? shipping_cost: BigDecimal.ZERO;
	}

	public void setShipping_cost(BigDecimal shipping_cost) {
		this.shipping_cost = shipping_cost;
	}

	public BigDecimal getCoupon_amount() {
		return coupon_amount != null ? coupon_amount: BigDecimal.ZERO;
	}

	public void setCoupon_amount(BigDecimal coupon_amount) {
		this.coupon_amount = coupon_amount;
	}
}
