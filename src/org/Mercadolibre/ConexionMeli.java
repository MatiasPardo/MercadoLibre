package org.Mercadolibre;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

import org.Mercadolibre.base.*;
import org.mercadolibretipos.*;
import com.google.gson.*;
import com.mercadolibre.sdk.*;
import com.ning.http.client.*;

public class ConexionMeli{
	
	private static final int LIMITE_RESULTADOS = 50;

	private String accessToken;
	
	private String refreshToken;
	 
	private int expiraEn;
	
	private Date dateLimit;
	
	private int userID;
	
	private Long appID;
	
	private String secretKey;
		
	private String folder;
		
	private String destino;

	private boolean borrarNovedades = true;
	
	private Meli instanciaMeli = null;
	
	public String getDestino() {
		return destino;
	}

	private void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getDateLimit() {
		return dateLimit;
	}

	private void setDateLimit(Date dateLimit) {
		this.dateLimit = dateLimit;
	}
	
	public String getFolder() {
		return folder;
	}

	private void setFolder(String folder) {
		this.folder = folder;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	private void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getAppID() {
		return appID == null ? Long.MIN_VALUE : appID; 
	}

	private void setAppID(Long appID) {
		this.appID = appID;
	}

	public String getSecretKey() {
		return secretKey;
	}

	private void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	private void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private void setExpiraEn(int expiraEn) {
		this.expiraEn = expiraEn;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getExpiraEn() {
		return expiraEn;
	}

	public int getUserID() {
		return userID;
	}
	public void setBorrarNovedades(boolean borrarNovedades) {
		this.borrarNovedades = borrarNovedades;
	}
	public boolean getBorrarNovedades() {
		return borrarNovedades;
	}
	
	private Meli meli(Long appid,String clientSecret, String accessToken,String RefreshToken) {
		Meli.apiUrl = "https://api.mercadolibre.com";
		this.setAppID(appid);
		this.setSecretKey(clientSecret);
		if (this.instanciaMeli == null){
			this.instanciaMeli = new Meli(appid,clientSecret,this.getAccessToken(),this.getRefreshToken());
		}
		else{
			this.instanciaMeli.initialize(appid,clientSecret,this.getAccessToken(),this.getRefreshToken());			
		}
		return this.instanciaMeli;
	}
	
	private Meli refreshMeli(){
		// primero se verifica que el token no expire
		try{
			this.conectarConML(this.getAppID(), this.getSecretKey(), this.getFolder(), this.getDestino());
		}
		catch(Exception e){			
		}
		return this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());
	}
	
	private void refresh() {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		calendario.add(Calendar.SECOND, this.getExpiraEn());
		this.setDateLimit(calendario.getTime());
	}
	
	public void desconectar(){
		if (this.instanciaMeli != null){
			this.instanciaMeli.close();
			this.instanciaMeli = null;
		}
	}
	
	public void conectarConML(Long _appID,String _secretKey,String folder,String destino) throws MeliException, IOException, AuthorizationFailure {
		
		if ((this.getDateLimit() != null) && (_appID.equals(this.getAppID()))) {			
			if(!this.getDateLimit().after(new Date())){
				// Se refresca el token
				if(this.getRefreshToken() == null){
					throw new MeliException("El refresh token se encuentra vacio.");
				}
				Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(),this.getRefreshToken());
				m.refreshAccessToken();
				this.setAccessToken(m.getAccessToken());
				this.setRefreshToken(m.getRefreshToken());
				this.setExpiraEn(21600);
				this.refresh();				
			}
		}
		else{		
			// Se conecta la primera vez
			this.setAppID(_appID);
			this.setSecretKey(_secretKey);
			Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(),this.getRefreshToken());
			this.setFolder(folder);
			Map<String,String> params = new HashMap<String,String>();
			params.put("grant_type","client_credentials");
			params.put("client_id",this.getAppID().toString());
			params.put("client_secret",this.getSecretKey());
			Response response = m.post("/oauth/token", params,"");
			JsonObject objeto = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
			this.setAccessToken(objeto.get("access_token").getAsString());
			try{
				this.setRefreshToken(objeto.get("refresh_token").getAsString());
			}catch(NullPointerException n){
				this.setRefreshToken(null);
			}
			this.setUserID(Integer.parseInt(objeto.get("user_id").getAsString()));
			this.setExpiraEn(Integer.parseInt(objeto.get("expires_in").getAsString()));
			this.refresh();				
		}
		this.setFolder(folder);
		this.setDestino(destino);
		
	}

	public JsonObject getNotificacion(JsonObject objeto1) throws MeliException, IOException 
	{
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		Response response = m.get(objeto1.get("resource").getAsString(),params);
   		JsonObject objeto = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
   		return	objeto;
	}
	
	public void procesarPedidos(ArrayList<MLPedido> pedidosAConfirmar) throws MeliException, IOException, AuthorizationFailure, ParseException 
	{
			
		String files;
        File[] listOfFiles = new File(this.getFolder()).listFiles(); 
        
        for (int i = 0; i < listOfFiles.length; i++) 
        {

            if (listOfFiles[i].isFile()) 
            {
                files = listOfFiles[i].getName();
                if (files.endsWith(".json") )
                {
                	FileReader miFile = null;
                	boolean moverFile = false;
                	String errorInstanciarPedido = null;
                	try {
	                	miFile = new FileReader(folder+"/"+files);
	                	JsonObject objeto = new Gson().fromJson(miFile,JsonObject.class);
	                	String resource = new String(objeto.get("resource").getAsString().substring(1,7));	                	
	                	if(resource.equals("orders")){
	                		try{
	                			pedidosAConfirmar.add(InstanciarPedidos.instanciarPedido(files, this.getNotificacion(objeto), this));
	                		}
	                		catch(Exception exPedido){
	                			moverFile = true;
	                			if (exPedido.getMessage() != null){
	                				errorInstanciarPedido = exPedido.getMessage();
	                			}
	                			else{
	                				errorInstanciarPedido = exPedido.toString();
	                			}
	                		}
	                	}
	                	else { 
	                		moverFile = true;  
	                	}
                	} catch(Exception e) {
                		moverFile = true;
                	} finally{
                		if (miFile != null){
                			miFile.close();
                		}
                		if (moverFile){
                			try{
                				this.moverArchivo(this.getFolder(), this.getDestino(), listOfFiles[i].getName());
                			}
                			catch(Exception e){                				
                			}
                		}
                	}
                	
                	if (errorInstanciarPedido != null){
                		throw new MeliException(new Exception("Error al buscar el pedido de notificacion " + files + ": " + errorInstanciarPedido));
                	}
                }
                
            }
          
        }
	}
	
	public void confirmarPedido(MLPedido pedido) throws MeliException, IOException, AuthorizationFailure, ParseException {
		String miPedido = pedido.getNombreArchivo();
		String files;
        File[] listOfFiles = new File(this.getFolder()).listFiles(); 
        
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            if (listOfFiles[i].isFile()) 
            {
            	try {
	                files = listOfFiles[i].getName();
	                if(files.equals(miPedido)) 
	                {
	                if(this.getBorrarNovedades()) {
	                	listOfFiles[i].delete();
	                }else {this.moverArchivo(this.getFolder(), this.getDestino(),files);}
	                	
	                }
	                
            	}catch(Exception e) {
            		
            	}
            }
        }
		
	}
	
	public void moverArchivo(String origen, String destino,String file) throws Exception{
		try {
	    File origenPatch = new File(origen+file);
	    File destinoPatch = new File(destino+file);
	    origenPatch.renameTo(destinoPatch);
		} catch(NullPointerException e) {
			System.out.println("el puntero esta en null");
		}
	}
	
	public JsonObject buscarIDPedido(String id) throws MeliException, IOException, AuthorizationFailure {		
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
	   	params.put("access_token", this.getAccessToken());
	   	Response response = m.get("/orders/"+id,params);
	   	String res = response.getResponseBody();	   	
	   	JsonObject resJson = new Gson().fromJson(res,JsonObject.class);
	   	JsonElement resElemn = resJson.get("error");
	   	if(resElemn != null && !resElemn.isJsonNull()){
	   		throw new MeliException("Error: "+resJson.toString());
	   	}
		return resJson; 		
	}
	
	
	public MLPedido buscarPedido(String id) throws Exception{
		JsonObject response = this.buscarIDPedido(id);
		return InstanciarPedidos.instanciarPedido(response, this);
	}
	
	public Order buscarPedidoParseado(String id) throws Exception{
		JsonObject response = this.buscarIDPedido(id);
		return new Gson().fromJson(response, Order.class);
	}
	
	public void actualizar(MLItem item) throws Exception{

	/*
	 * La idea es meterle un poco de logica aca para que no actualice nada cuando no corresponde y sacarla del metodo siguiente
	 */
		
		MLItem itemActual =this.buscarMLItemMercadoLibre(item.getIdItem()); 
		boolean debeActualizarStock = Boolean.FALSE;
		boolean debeActualizarPrecio = Boolean.FALSE;

		if(item.getStock() != null){

			if(item.getVariacionSeleccionada() != null && !item.getVariacionSeleccionada().isEmpty()){
				itemActual.setVariacionSeleccionada(item.getVariacionSeleccionada());
			}
			if(itemActual.getVariacionSeleccionada() != null && !itemActual.getVariacionSeleccionada().isEmpty() && itemActual.getVariaciones() != null){
				BigDecimal stockDeVariacion = null;
				for(MLVariacionItem var: itemActual.getVariaciones()){
					if(var.getId().equals(itemActual.getVariacionSeleccionada())){
						stockDeVariacion = new BigDecimal(var.getAvailable_quantity());
						break;
					}
				}
				debeActualizarStock = this.sonIguales(stockDeVariacion, item.getStock());
			}else{
				debeActualizarStock = this.sonIguales(itemActual.getStock(), item.getStock());

			}
		}
		
		if(item.getPrecio() != null){
			debeActualizarPrecio = this.sonIguales(itemActual.getPrecio(), item.getPrecio());
		}
		
		if(debeActualizarStock || debeActualizarPrecio){
			this.actualizarLoAsignado(item,itemActual);
		}

	}
	
	private boolean sonIguales(BigDecimal precio, BigDecimal precio2) {
		if(precio.compareTo(precio2) != 0){
			return true;
		}else{
			return false;
		}
	}

	
	public void actualizarLoAsignado(MLItem item, MLItem itemActual) throws Exception {
		
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		String body = null;
		JsonObject conVariantes = new JsonObject();
		JsonArray variaciones = new JsonArray();
		
		if(item.getVariacionSeleccionada() != null){
			itemActual.setVariacionSeleccionada(item.getVariacionSeleccionada());
		}
		
		if (itemActual.getVariaciones() != null && itemActual.getVariaciones().size() >= 1 && itemActual.getVariacionSeleccionada() != null){

			body = actualizarConVariante(item, itemActual, conVariantes, variaciones);
		
		}
		else{
			body = actualizarSinVariante(item);
		}
		
		JsonObject jsonResponse = new JsonObject();
		try {
			String response = m.put("/items/"+item.getIdItem(), params,body).getResponseBody();
			jsonResponse = new Gson().fromJson(response, JsonObject.class); 
			if(jsonResponse.get("id") == null){
				StringBuilder error = new StringBuilder();
				error.append("error: ");
				error.append(jsonResponse.get("message").toString());
				error.append(jsonResponse.get("error").toString());
				error.append("causa:");
				JsonArray causaError = jsonResponse.get("cause").getAsJsonArray();
				if(causaError != null && causaError.size() >= 1){
					error.append(causaError.get(0).getAsJsonObject().get("message").toString());
				}
				if(itemActual.getVariacionSeleccionada() == null && itemActual.getVariaciones().size() > 1){
					throw new MeliException("Esta tratando de actualizar una publicacion con mas de una variante y no tiene cargada ninguna en la publicacion");
				}
				throw new MeliException(error.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

			throw new MeliException(jsonResponse.toString());
		}
		
	}

	private String actualizarConVariante(MLItem item, MLItem itemActual, JsonObject conVariantes, JsonArray variaciones)
			throws MeliException {
		String body;
		boolean varianteCorrecta = false;
		String idVarianteSelec = null;
		if(itemActual.getVariacionSeleccionada() != null && !itemActual.getVariacionSeleccionada().isEmpty()){
			idVarianteSelec = itemActual.getVariacionSeleccionada();
		}
		for(MLVariacionItem var: itemActual.getVariaciones()){
			JsonObject variacion = new JsonObject();
			variacion.addProperty("id", var.getId());
			if(item.getPrecio() != null){
				variacion.addProperty("price",item.getPrecio());
			}
			if(item.getStock() != null){
				variacion.addProperty("available_quantity",var.getAvailable_quantity());
			}
			if(idVarianteSelec.equals(var.getId()) ){
				varianteCorrecta = true;
				if(item.getStock() != null){
					variacion.addProperty("available_quantity",item.getStock());
				}
			}
			variaciones.add(variacion);

		}                                                                                                                                                                                                                                                                                                     
		if(!varianteCorrecta && idVarianteSelec != null){
			throw new MeliException("La variante seleccionada no esta en la publicacion que desea actualizar");
		}
		conVariantes.add("variations", variaciones);
		body = conVariantes.toString();
		return body;
	}

	private String actualizarSinVariante(MLItem item) {
		String body;
		JsonObject bodyJson = new JsonObject();
		if(item.getPrecio() != null){
			bodyJson.addProperty("price",item.getPrecio());
		}
		if (item.getStock() != null){
			bodyJson.addProperty("available_quantity", item.getStock());
		}
		body = bodyJson.toString();
		return body;
	}
	
	private JsonObject buscarMLItem(String idItem) throws Exception{
    	Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
	   	params.put("access_token", this.getAccessToken());
	   	Response response = m.get("/items/" + idItem, params);
		if (response.getStatusCode() == 200){
			try{
				JsonObject objetoItem = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
				return objetoItem;				
			}
			catch(Exception e){
				throw new Exception("Error al buscar item "  + idItem + ": " + e.toString());
			}
		}
		else{
			throw new Exception("Error al buscar item " + idItem + ": " + response.getStatusText());			
		}
    }
	
    public MLItem buscarMLItemMercadoLibre(String idItem) throws Exception{
    	JsonObject objetoItem = this.buscarMLItem(idItem);
		MLItem itemML = MLItem.crear(objetoItem, idItem);
		return itemML;			
    }
    
    public MLItem buscarMLItemWithInformationMercadoLibre(String idItem) throws Exception{
    	JsonObject objetoItem = this.buscarMLItem(idItem);
    	MLItem itemML = MLItem.crear(objetoItem, idItem);
    	itemML.setItem(MLItemInformation.crear(objetoItem));
    	return itemML;
    }
    
    public String categorias() throws IOException, MeliException {
		Meli m = this.refreshMeli();
		return m.get("/sites/MLA/categories").getResponseBody();
	}
    
	public String categorias(String category_id) throws Exception {
		Meli m = this.refreshMeli();
		return m.get("/categories/"+category_id+"").getResponseBody();
	}
	
	public String publicar(MLItem item) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		Response response = m.post("/items", params, item.toJsonObject().toString());
    	JsonObject respons = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
    	if(response.getStatusCode() == 201) {
    		item.setIdItem(respons.get("id").getAsString());
    		return response.getResponseBody();
    	}
    	else{
    		throw new Exception(response.getResponseBody());
    	}	
	}
	
	public String republicar(String idItem, MLItem itemNuevo) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		JsonObject body = new JsonObject();
   		body.addProperty("price", itemNuevo.getPrecio());
   		body.addProperty("quantity", itemNuevo.getStock());
   		body.addProperty("listing_type_id", itemNuevo.getItem().getListing_type_id().getId());
   		
		Response response = m.post("/items/"+idItem+"/relist", params, body.toString());
		JsonObject respons = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
    	if(response.getStatusCode() == 201) {
    		itemNuevo.setIdItem(respons.get("id").getAsString());
    		return response.getResponseBody();
    	}
    	else{
    		throw new Exception(response.getResponseBody());
    	}	
	}
	
	public String modificarPublicacion(String idItem,Map<String,Object> mapa) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		JsonObject attributo1 = new JsonObject();
   		Iterator<Entry<String, Object>> it = mapa.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String,Object> entry = (Entry<String,Object>) it.next();
			attributo1.addProperty(entry.getKey(),entry.getValue().toString());
		}		
		return m.put("/items/"+idItem, params,attributo1.toString()).getResponseBody();
	}
	
	public String pausaPublicacion(String item_id) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
		String body = new String("{\"status\":\"paused\"}");
		return (m.put("/items/"+item_id, params,body)).getResponseBody();
	}
	
	public String activaPublicacion(String item_id) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
	   	params.put("access_token", accessToken);
		String body = new String("{\"status\":\"active\"}");
		String res = (m.put("/items/"+item_id, params,body)).getResponseBody();
		return res;
	}
	
	public String eliminaPublicacion(String item_id, boolean soloCerrar) throws Exception {
		Meli m = this.refreshMeli();
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
		String body = new String("{\"status\":\"closed\"}");		
		Response response = m.put("/items/"+item_id, params,body);
		
		if (!soloCerrar){
			m = this.refreshMeli();
			params = new HashMap<String,String>();
			params.put("access_token", accessToken);
			body = new String("{\"deleted\":\"true\"}");
			response = m.put("/items/"+item_id, params,body);
		}
		
		return response.getResponseBody();
	}
	
	public String modificarDescripcion(String idItem,String descripcion) throws Exception {
    	
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(),this.getRefreshToken());
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		JsonObject attributo1 = new JsonObject();
   		attributo1.addProperty("plain_text",descripcion);
		return m.put("/items/"+idItem+"/description", params,attributo1.toString()).getResponseBody();
	
	}
	
	public String obtenerIdImagen(String url) throws IOException, MeliException {
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(),this.getRefreshToken());
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		JsonObject picturs = new JsonObject();
   		picturs.addProperty("source", url);
   		return new Gson().fromJson(m.post("/pictures", params,picturs.toString()).getResponseBody(),JsonObject.class).get("id").getAsString(); //convertir a json y obtener el id.
	}
	
	public MLPicture subirImagen(String url) throws Exception{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(),this.getRefreshToken());
		Map<String,String> params = new HashMap<String,String>();
   		params.put("access_token", accessToken);
   		JsonObject picturs = new JsonObject();
   		picturs.addProperty("source", url);
   		Response response = m.post("/pictures", params,picturs.toString());
   		if(response.getStatusCode() == 201) {
   			JsonObject respons = new Gson().fromJson(response.getResponseBody(),JsonObject.class);
   			MLPicture picture = new MLPicture();
   			picture.setId(respons.get("id").getAsString());
   			return picture;   			
   		}
   		else{
   			throw new Exception(response.getResponseBody());
   		}   		
	}
	
	public String refrescarTokenNuevoUsuario(String ultimoRefreshToken) throws MeliException, IOException{
			Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());				
			Map<String,String> params = new HashMap<String,String>();
		  	params.put("grant_type","refresh_token");
			params.put("refresh_token",ultimoRefreshToken);
			params.put("client_id",new String(this.getAppID().toString()));
			params.put("client_secret", this.getSecretKey());
			Response res = m.post("/oauth/token", params,"");
			JsonObject result = JsonParser.parseString(res.getResponseBody()).getAsJsonObject();
			try{
			this.setAccessToken(result.get("access_token").getAsString());
			this.setRefreshToken(result.get("refresh_token").getAsString());
			this.setUserID(Integer.parseInt(result.get("user_id").getAsString()));
			this.setExpiraEn(Integer.parseInt(result.get("expires_in").getAsString()));
			this.refresh();
			}catch(Exception e){
				return result.toString();
			}
		return result.toString();
	}
	public String conectarPorPrimeraVezConOtroUsuario(String code) throws MeliException, IOException{
		
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());				
		Map<String,String> params = new HashMap<String,String>();
	  	params.put("grant_type","authorization_code");
		params.put("client_id",new String(this.getAppID().toString()));
		params.put("client_secret", this.getSecretKey());
		params.put("code",code);
		params.put("redirect_uri", "https://www.google.com.ar/");
		Response res = m.post("/oauth/token", params,"");
		JsonObject result = JsonParser.parseString(res.getResponseBody()).getAsJsonObject();
		try{
		this.setAccessToken(result.get("access_token").getAsString());
		this.setRefreshToken(result.get("refresh_token").getAsString());
		this.setUserID(Integer.parseInt(result.get("user_id").getAsString()));
		this.setExpiraEn(Integer.parseInt(result.get("expires_in").getAsString()));
		this.refresh();
		}catch(Exception e){
			return res.getResponseBody();
		}
	return result.toString();
	}
	
	public String enviarMensajePostVenta(String compradorUserID, String mensaje, String orderID) throws MeliException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());				
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", this.getAccessToken());
		JsonObject body = new JsonObject();
		JsonObject from = new JsonObject();
		JsonObject to = new JsonObject();
		from.addProperty("user_id", this.getUserID());
		from.addProperty("email", "");
		body.add("from", from);
		to.addProperty("user_id", compradorUserID);
		body.add("to", to);
		body.addProperty("text", mensaje);
		Response response = m.post("/messages/packs/"+orderID+"/sellers/"+this.getUserID(),params,body.toString());
		return response.getResponseBody();
		}
	
	public String consultarVentas() throws MeliException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());				
		Map<String,String> params = new HashMap<String,String>();
		String user = String.valueOf(this.getUserID());
		params.put("seller", user);
		params.put("access_token", this.accessToken);
		Response response = m.get("/orders/search", params);
		return response.getResponseBody();
	}
	public String consultarVentaID(String ventaID) throws MeliException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());				
		Map<String,String> params = new HashMap<String,String>();
		String user = String.valueOf(this.getUserID());
		params.put("seller", user);
		params.put("order_id", ventaID);
		params.put("access_token", this.accessToken);
		Response response = m.get("/orders/search", params);
		return response.getResponseBody();
	}
	public String crearUsuarioTest() throws MeliException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", m.getAccessToken());
		JsonObject prueba = new JsonObject();
		prueba.addProperty("site_id", "MLA");
		Response response = m.post("/users/test_user", params,prueba.toString());
		return response.getResponseBody();
		
	}
	public String consultaUsuario(String nickName) throws MeliException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		Map<String,String> params = new HashMap<String,String>();
		params.put("nickname", nickName);
		Response response = m.get("/sites/MLA/search", params);
		return response.getResponseBody();
		
	}
	
	public Billing_info buscarInformacionFacturacion(String idPedido) throws MeliException, JsonSyntaxException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", this.getAccessToken());
		Response response = m.get("/orders/"+idPedido+"/billing_info",params);
		return new Gson().fromJson(response.getResponseBody(), Billing_info.class);
		
	}
	
	public boolean esResponsableInscripto(String idPedido){
		boolean esResponsableInscripto = false;
		Billing_info info = null;
		try {
			info = this.buscarInformacionFacturacion(idPedido);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(info != null && info.getAdditional_info() != null){
			for(Additional_info inf: info.getAdditional_info()){
				if(inf.getType() != null && inf.getType().equals("TAXPAYER_TYPE_ID")){
					if(inf.getValue() != null && inf.getValue().equals("IVA Responsable Inscripto")){
						esResponsableInscripto = true;
					}
				}
			}
		}
		return esResponsableInscripto;
	}
	//devuelve el id de la factura cargada
    public String cargarFactura(String idPedido, String fullFileName) throws Exception {
    	Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		Response response = null;
		try {
			response = m.postFile(idPedido, fullFileName);
		} catch (MeliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(response.getStatusCode() == 201){
			return new Gson().fromJson(response.getResponseBody(), JsonObject.class).get("ids").getAsString();
		}else throw new Exception(response.getResponseBody());
    }

    public JsonObject getCustom(String resource, String key, String resource2, Map<String,String> params) throws MeliException, JsonSyntaxException, IOException{
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		params.put("access_token", this.getAccessToken());
		Response response = null;
		if(resource2 == null){
			 response = m.get("/"+resource,params);
		}else{
			 response = m.get("/"+resource+"/"+key+"/"+resource2,params);
		}
		JsonObject resJson = new Gson().fromJson(response.getResponseBody(), JsonObject.class);
		return resJson;
    }
    
	public List<Notificacion> buscarNotificaciones(Date desde, Date hasta) throws JsonSyntaxException, MeliException, IOException{
		Map<String,String> params = new HashMap<String,String>();
		List<Notificacion> notificaciones = new LinkedList<Notificacion>();
		params.put("app_id", String.valueOf(appID));
		params.put("topic", "orders");
		//params.put("offset", "500");
		//params.put("limit", "500");
		JsonObject response = this.getCustom("myfeeds", "", null, params);
		for(JsonElement element: response.get("messages").getAsJsonArray()){
			notificaciones.add(new Gson().fromJson(element, Notificacion.class));
		}
		return notificaciones;
	}
	
	public void buscarPedidos(List<MLPedido> pedidosAConfirmar,Date desde, Date hasta) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(desde);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("seller", String.valueOf(this.getUserID()));
		params.put("order.date_created.from", ""+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+"T00:00:00Z");
		cal.setTime(hasta);
		params.put("order.date_created.to", ""+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+"T00:00:00Z");
		params.put("offset","0");
		params.put("limit", String.valueOf(LIMITE_RESULTADOS)); 
		JsonObject response = this.getCustom("orders", "", "search", params);
		Map<String,List<MLPedido>> pedidosDeCarrito = new HashMap<String,List<MLPedido>>();
		for(JsonElement element: response.get("results").getAsJsonArray()){
			MLPedido pedido = InstanciarPedidos.instanciarPedido(element.getAsJsonObject(),this);
			if(pedido.getPackId() != null){
				if(!pedidosDeCarrito.containsKey(pedido.getPackId())){
					List<MLPedido> pedidosNuevos = new LinkedList<MLPedido>();
					pedidosNuevos.add(pedido);
					pedidosDeCarrito.put(pedido.getPackId(),pedidosNuevos);
				}
				else{
					pedidosDeCarrito.get(pedido.getPackId()).add(pedido);
				}
					
			}else{
				pedidosAConfirmar.add(pedido);
			}
		}
		pedidosDeCarrito.forEach((pack,pedidos)->{
			MLPedido pedidoCarrito = pedidos.get(0); 
			pedidoCarrito.setNumeroDePedido(new Long(pedidoCarrito.getPackId()));
			try {
				pedidoCarrito.setShipping_cost(this.costoEnvio(pedidoCarrito.getShipping()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Boolean primerPedido = true;
			for(MLPedido pedido: pedidos){
				if(!primerPedido) pedidoCarrito.getItems().addAll(pedido.getItems());
				primerPedido = false;
			}
			//quizar
			pedidosAConfirmar.add(pedidoCarrito);
		});
		
		int total = response.get("paging").getAsJsonObject().get("total").getAsInt();
		int llamados = total / LIMITE_RESULTADOS;
		
		for(int i =1;i<(llamados+1);i++){
			params.put("offset",String.valueOf(50*i));
			response = this.getCustom("orders", "", "search", params);
			for(JsonElement element: response.get("results").getAsJsonArray()){
				pedidosAConfirmar.add(InstanciarPedidos.instanciarPedido(element.getAsJsonObject(),this));
			}
		}
		
	}
	
	public BigDecimal costoEnvio(String idShipment) throws MeliException, JsonSyntaxException, IOException{
		BigDecimal costoEnvio = BigDecimal.ZERO;
		Meli m = this.meli(this.getAppID(), this.getSecretKey(), this.getAccessToken(), this.getRefreshToken());					
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", this.getAccessToken());
		Response response = m.get("/shipments/"+idShipment+"/costs",params);
		Shipment shipment = new Gson().fromJson(response.getResponseBody(), Shipment.class);
		if(shipment.getReceiver() != null && shipment.getReceiver().getCost() != null){
			BigDecimal costo = shipment.getReceiver().getCost();
			if(costo.compareTo(BigDecimal.ZERO) != 0 ){
				costoEnvio = costo;
			}
		}
		return costoEnvio;
		
	}
}
