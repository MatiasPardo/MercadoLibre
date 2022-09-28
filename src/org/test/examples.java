package org.test;

import java.text.*;
import java.util.*;
import org.Mercadolibre.*;
import com.mercadolibre.sdk.*;

/**
 * @author Matias Pardo
 *
 */

public class examples {
	
	@SuppressWarnings("unused")
	private static final String URL_NOTIFICACIONES_Cloud = "https://clouderpgo.com:8543/CloudERP/notificacionesml.jsp?esquema=PruebaCarestino";
	@SuppressWarnings("unused")
	private static final String URL_NOTIFICACIONES_Kiero = "http://kierosrl.ddns.net:8080/Kiero/notificacionesml.jsp";
	@SuppressWarnings("unused")
	private static final String URL_NOTIFICACIONES_WatchLand= "http://watchlanderp.ddns.net:8080/WatchLandERP/notificacionesml.jsp";
	
	
	
	public static void main(String[] args) throws MeliException, Exception
	{
		
		//DosLs - Techland
//		long appID = new Long("8172347023822978");
//		String SecretKey = new String("AxQumlR4gkfFiT3rFXTzU1GbyBh3iQ6i");
//		
//		Enogarage - PruebasCarestino
//		long appID = new Long("6515578984788488");
//		String SecretKey = new String("DNqce4bqllTElVArpsvVaGMMql10LX05");
//		
		//garbagnati NUEVO
//		long appID = new Long("955189498136353");
//		String SecretKey = new String("Ssyn2EjpPguDOnQoUmZWu9zH6vUprsdR");
////		
	//	garbagnati viejo NO FUNCIONA
//		long appID = new Long("1475883860752543");
//		String SecretKey = new String("ukjr9rkDUJTANeoUiRn5LlPSeYsd6i7N");

//		// Wasser
//		long appID = new Long("3139508294166472");
//		String SecretKey = new String("26NhTPlTw4V1iEH3V9a1dfjj6Kx6rts6");
		// Wasser 2
//		long appID = new Long("8209740059394283");
//		String SecretKey = new String("silPj8C8sOvlmA28cbZNsNAcIWe6U0pR");
		
		//wasser - goldtz
//		long appID = new Long("8646958416347557");
//		String SecretKey = new String("MkCtJLF93akPf2KBDSCDZQf8tRglNvvG");

		
		//sonidoiluminacion
		long appID = new Long("1699957472917890");
		String SecretKey = new String("MEO66G6XYpbPehDUqiSVCP2Iv2gK6rvR");
		 
		//WartchLand
//		long appID = new Long("2446510094950223");
//		String SecretKey = new String("nBNPdKDWPWWXba5ZMf6SdVbXfaYRqUaL");
		
//		 
		//Kiero1
//		long appID = new Long("8298296837431984");
//		String SecretKey = new String("NBhUyPvynl6gjMtThzKAc9PMASPnRPSE");
		
		//Kiero2
//		long appID = new Long("8013659811299908");
//		String SecretKey = new String("7wobCvmmoqLY5Q4jGtK4MlZVbfWYjBPT");
		
		//Kiero3
//		long appID = new Long("19588934628991");
//		String SecretKey = new String("N9FkiR2mRs7Y7IRx95KbHdzOf1mHav4P");
		
		ConexionMeli miConexion = new ConexionMeli();
		String origen = new String("C:/Users/Matias/Downloads/notificaciones/");
		String destino = new String("C:/Users/Matias/Downloads/notificaciones/");
		miConexion.conectarConML(appID, SecretKey, origen, destino);
//		miConexion.setUserID(149525087);
//		List<MLPedido> pedidosAConfirmar = new LinkedList<MLPedido>();
//		Date desde = stringToDate("2022-05-01");
//		Date hasta = stringToDate("2022-05-30");
//		MLItem item = new MLItem("MLA695058507");
//		item.setVariacionSeleccionada("52583780475");	
//		item.setStock(new BigDecimal(0));
//		item.setPrecio(new BigDecimal(1398));
//		miConexion.actualizar(item);
//		miConexion.pausaPublicacion("MLA695058507");
//		System.out.print(miConexion.buscarIDPedido("5244885701")+"\n");
//		List<MLPedido> pedidos = new LinkedList<MLPedido>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String desdeString = "2022-03-20";
//		String hastaString = "2022-03-25";
		try{
	//		Date desde = sdf.parse(desdeString);
	//		Date hasta = sdf.parse(hastaString);
//			miConexion.setUserID(638274950);
//			miConexion.refrescarTokenNuevoUsuario(ultimoRefreshToken)
			System.out.print(miConexion.getAccessToken() + "\n"+ miConexion.getUserID() +"\n");
	
			System.out.println(miConexion.crearUsuarioTest());
//			MLPedido miPedido = miConexion.buscarPedido("2000003741946063");
//			System.out.println(miPedido.getShipping_cost());
//			System.out.println(miPedido.getShipping());
//			Boolean isPack = miPedido.getPackId() != null ;
//			System.out.println("Venta carrito: " + isPack+ " - " + miPedido.getNumeroDePedido());
//			MLItem publicacion = miConexion.buscarMLItemMercadoLibre("MLA1122221614");
//			System.out.println(publicacion.getIdItem());
//			System.out.println(miConexion.costoEnvio(miPedido.getShipping()));
	
//			miConexion.buscarPedidos(pedidos, desde, hasta);
//			for(MLPedido pedido: pedidos){
//				System.out.print(pedido.getNumeroDePedido() + " " + pedido.getPackId() + " " + pedido.getItems().size() +"\n");
//			}
//			System.out.print(miConexion.costoEnvio("41309158541"));
//			miConexion.buscarPedidos(pedidos, desde, hasta);
//			for(MLPedido pedido: pedidos){
//				System.out.print("Numero Pedido: " + pedido.getNumeroPedido() + "\n");
//				System.out.print("Numero de Pedido: " + pedido.getNumeroDePedido() + "\n");
//	 		}
			
			
			/**
			 *
			 *
			 *
			 *					ARBA - COT
			 *
			 *
			 *
			 */
//			examples prueba = new examples();
//			
//			String respone = prueba.pruebasCot("", "C:/Users/Matias/Documents/TB_33714210349_000000_20220404_000005.txt", miConexion);
//			
//			System.out.print(respone);
			
			/**
			 *
			 *
			 *
			 *					ARBA - COT
			 *
			 *
			 *
			 */
		}finally {
			miConexion.desconectar();

		}
		//System.out.print();
		//miConexion.actualizarPrecio(item);
	
		
		/*Map<String,String> params = new HashMap<String, String>();
		
		System.out.print(response);*/
//		System.out.print(miConexion.getAccessToken()+"\n");
//		System.out.print(miConexion.getRefreshToken()+"\n");
//		miConexion.setUserID(userid);
//		System.out.println(miConexion.conectarPorPrimeraVezConOtroUsuario("TG-5f5146c08ef46d000793902e-638359754"));
		/*int count = 0;
		for(Notificacion n: miConexion.buscarNotificaciones(new Date(), new Date())){
			count++;
			System.out.print(n.getResource());
		}

		System.out.print(count+"\n");
*/
		
	//	Response response = m.get("/orders/4020378066", params);
    //	System.out.println(response.getResponseBody());
    	
//token de user test
//APP_USR-1699957472917890-090319-4989594cc9a703e9a8a6b49a885697c3-638359754

/*		FluentStringsMap params = new FluentStringsMap();
		params.add("access_token", miConexion.getAccessToken());
		JsonObject prueba = new JsonObject();
    	prueba.addProperty("site_id", "MLA");
    	Response response = m.post("/users/test_user", params,prueba.toString());
    	System.out.println(response.getResponseBody());
//{"id":638274950,"nickname":"TEST5IMIXHNO","password":"qatest7530","site_status":"active","email":"test_user_18419565@testuser.com"}
//APP_USR-1699957472917890-090318-4b17c0405744848a7c5336350d0bdf27-18688378
//token 03/09/20 hs-> 12:30
//{"id":638359754,"nickname":"TESTWFGEDE3M","password":"qatest8654","site_status":"active","email":"test_user_25946025@testuser.com"}
//{"id":667365277,"nickname":"TEST9AEW1JKO","password":"qatest5548","site_status":"active","email":"test_user_53915643@testuser.com"}
//{"id":667365602,"nickname":"TETE3263872","password":"qatest5231","site_status":"active","email":"test_user_11502078@testuser.com"}
 *{"id":689962095,"nickname":"TETE4367067","password":"qatest3011","site_status":"active","email":"test_user_62359563@testuser.com"}
*/		
//		System.out.println(miConexion.getAccessToken());
	}
	
		
/*		ArrayList<MLPedido> pedidosAConfirmar = new ArrayList<MLPedido>();
		
		miConexion.procesarPedidos(pedidosAConfirmar);
		System.out.println(pedidosAConfirmar.get(0).getItems().getTitulo());
*/		
		
	
	
	//	public String getPruebas() throws MeliException, IOException {
	//	Meli m = this.meli(this.getAppID(), this.getSecretKey(), ,this.getRefreshToken());
	//	FluentStringsMap params = new FluentStringsMap();
	//	params.add("access_token", this.getAccessToken());
		
	//	params.add("user_id", "18688378");
	//	Response response = m.get("/orders/1680789656", params);
		
	/*	FluentStringsMap params = new FluentStringsMap();
		params.add("access_token", this.getAccessToken());
 
		JsonObject prueba = new JsonObject();
    	prueba.addProperty("site_id", "MLA");
    	
    	Response response = m.post("/users/test_user", params, prueba.toString());
    	System.out.println(response.getResponseBody());
	*/
		
	//	return response.getResponseBody();
	//}

		public static Date stringToDate(String yyyyMMdd){

		    Date result = null;
		    try{
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        result  = dateFormat.parse(yyyyMMdd);
		    }

		    catch(ParseException e){
		        e.printStackTrace();

		    }
		    return result ;
		}
	
}




