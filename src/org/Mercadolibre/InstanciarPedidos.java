package org.Mercadolibre;

import java.io.*;
import java.math.*;
import java.util.*;
import java.text.*;

import com.google.gson.*;
import com.mercadolibre.sdk.*;

public class InstanciarPedidos {
	
	
	public static MLPedido instanciarPedido(String file,JsonObject objeto, ConexionMeli conexion) throws MeliException, IOException, AuthorizationFailure, ParseException {
		
		MLPedido pedido = new MLPedido();
		pedido = InstanciarPedidos.instanciarPedido(objeto, conexion);
		pedido.setNombreArchivo(file);
		
 	 	return 	pedido;
 	 	

	}
	
	
	public static MLPedido instanciarPedido(JsonObject objeto, ConexionMeli conexion) throws MeliException, IOException, AuthorizationFailure, ParseException {
			
		MLPedido pedido = new MLPedido();
		
		JsonElement pack = objeto.get("pack_id");
		if(pack != null && !pack.isJsonNull()){
			pedido.setPackId(String.valueOf(pack.getAsBigInteger()));
		}
		
		//numero de pedido
		pedido.setNumeroDePedido(objeto.get("id").getAsLong());
		
 		//Id de usuario comprador
		JsonObject buyer = objeto.get("buyer").getAsJsonObject();
		pedido.setIdUsuarioComprador(buyer.get("id").getAsString());
 		//Nombre del usuario de comprador
		pedido.setNombreUsuarioCompra(buyer.get("nickname").getAsString());
 		
		if(buyer.get("first_name") != null){
			pedido.setNombreComprador(buyer.get("first_name").getAsString());
		}
		if(buyer.get("last_name") != null){
			pedido.setApellidoComprador(buyer.get("last_name").getAsString());
		}
		if(buyer.get("email") != null){
			pedido.setEmailComprador(buyer.get("email").getAsString());
		}
		if(buyer.get("billing_info") != null){
			JsonObject billing_info = buyer.get("billing_info").getAsJsonObject();
			if(billing_info.get("doc_type") != null){
				pedido.setTipoDocumentoComprador(billing_info.get("doc_type").getAsString());
			}
			if(billing_info.get("doc_number") != null){
				pedido.setNumeroDocumentoComprador(billing_info.get("doc_number").getAsString());
			}
		}
		//setear fecha	 	
		if(objeto.get("date_created") != null){
			pedido.setFechaCreacion(InstanciarPedidos.convertirADate(objeto.get("date_created").getAsString()));
		}
 		if((objeto.get("date_closed") != null && objeto.get("date_closed").isJsonNull())) {
 			pedido.setFecha(null);
 			pedido.setFechaCierre(null);
 		}else {
 			pedido.setFechaCierre(InstanciarPedidos.convertirADate(objeto.get("date_closed").getAsString()));	 		
	 		Calendar cal = Calendar.getInstance();
	 		//locale-specific 
	 		cal.setTime(pedido.getFechaCierre()); 
	 		cal.set(Calendar.HOUR_OF_DAY, 0); 
	 		cal.set(Calendar.MINUTE, 0); 
	 		cal.set(Calendar.SECOND, 0); 
	 		cal.set(Calendar.MILLISECOND, 0); 
	 		Date time = cal.getTime();
	 		pedido.setFecha(time);
 		
 		}
 		//items
 	 	Collection<MLItemPedido> misItems = InstanciarPedidos.instanciarPItem(objeto,conexion,pedido);
 	 	
 	 	pedido.setItems(misItems);
 	 	
 	 	if(objeto.get("total_amount") != null && !objeto.get("total_amount").isJsonNull()){
 	 		pedido.setPrecioTotal(objeto.get("total_amount").getAsBigDecimal());
 	 	}
 	 	
		JsonElement shipOb = objeto.get("shipping");
		if(shipOb != null && !shipOb.isJsonNull()){
			JsonElement shipping = shipOb.getAsJsonObject().get("id");
			if(shipping != null && !shipping.isJsonNull()){
				pedido.setShipping(String.valueOf(shipping.getAsLong()));
				pedido.setShipping_cost(conexion.costoEnvio(pedido.getShipping()));
			}
		}
		
		JsonElement estado = objeto.get("status"); 
		if(estado != null && !estado.isJsonNull()){
			if(estado.getAsString().toLowerCase().equals("cancelled")){
				pedido.setCancelada(Boolean.TRUE);
			}
		}
		
		JsonElement payment = objeto.get("payments");
		if(payment != null && !payment.isJsonNull()){
			BigDecimal costoEnvio = BigDecimal.ZERO;
			for(JsonElement elem: payment.getAsJsonArray()){
				JsonElement cuponDescuento = elem.getAsJsonObject().get("coupon_amount");
				pedido.setCoupon_amount(cuponDescuento.getAsBigDecimal());
				JsonElement costo = elem.getAsJsonObject().get("shipping_cost");
				if(costo != null && !costo.isJsonArray()){
					costoEnvio = costoEnvio.add(costo.getAsBigDecimal());
				}
			}
			if(pedido.getShipping() == null){
				pedido.setShipping_cost(costoEnvio);
			}
		}
		
		
		
 	 	return pedido;
	}

	public static List<MLItemPedido> instanciarPItem(JsonObject objeto, ConexionMeli conexion, MLPedido pedido) {
		
		
		MLItemPedido item = new MLItemPedido();
		
		JsonObject misItems = objeto.get("order_items").getAsJsonArray().get(0).getAsJsonObject();
		JsonObject miItem = misItems.get("item").getAsJsonObject();
		
		//numero de item y pedido cuando es una venta de carrito
		if(pedido.getPackId() != null){
			item.setPackOrder(pedido.getNumeroDePedido());
		}
		item.setIdItem(miItem.get("id").getAsString());
		
		double precio = Double.parseDouble(misItems.get("unit_price").getAsString());
		item.setPrecio(precio);
		BigDecimal cantidad = new BigDecimal(misItems.get("quantity").getAsString());
		item.setCantidad(cantidad);
		item.setCategoriaId(miItem.get("category_id").getAsString());
		item.setTitulo(miItem.get("title").getAsString());
		if(miItem.get("condition").getAsString().equals("used")) {
			item.setNuevo(false);
		}else item.setNuevo(true);
		
		if(miItem.get("warranty") != null && miItem.get("warranty").isJsonNull()) {
			item.setGarantia(false);
		}else item.setGarantia(true);
		
		
		JsonElement elVariation = miItem.get("variation_id");
		if(elVariation != null && !elVariation.isJsonNull()){
			item.setIdVariante(elVariation.getAsString());
		}
		
		List<MLItemPedido> items = new LinkedList<MLItemPedido>();
		items.add(item);
		
		return items;
		
}


	private static Date convertirADate(String string) throws ParseException{
			Date fechaFinal = null;
			if(string != null){
				String fecha = string.substring(0,10);
				String hora = string.substring(11,19);
				String gmt = string.substring(23,29);
				String fechaHoraGmt = new String(fecha+" "+hora+" "+gmt);
				SimpleDateFormat fech = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX"); 
				fechaFinal = fech.parse(fechaHoraGmt);
			}

			return fechaFinal;
			
			
		}

}
