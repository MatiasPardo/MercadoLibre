package com.mercadolibre.sdk;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.Mercadolibre.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.multipart.*;



public class Meli implements ConnectionResource {

	public static String apiUrl = "https://api.mercadolibre.com";

	/**
	 *	Availables auth sites. One user - application can only operate in one site
	 *
	 */

	public static enum AuthUrls {
		MLA("https://auth.mercadolibre.com.ar"), // Argentina 
		MLB("https://auth.mercadolivre.com.br"), // Brasil
		MCO("https://auth.mercadolibre.com.co"), // Colombia
		MCR("https://auth.mercadolibre.com.cr"), // Costa Rica
		MEC("https://auth.mercadolibre.com.ec"), // Ecuador
        MLC("https://auth.mercadolibre.cl"), 	// Chile
		MLM("https://auth.mercadolibre.com.mx"), // Mexico
		MLU("https://auth.mercadolibre.com.uy"), // Uruguay
		MLV("https://auth.mercadolibre.com.ve"), // Venezuela
		MPA("https://auth.mercadolibre.com.pa"), // Panama
		MPE("https://auth.mercadolibre.com.pe"), // Peru
		MPT("https://auth.mercadolibre.com.pt"), // Portugal
		MRD("https://auth.mercadolibre.com.do"); // Dominicana
		
		private String value;

                private AuthUrls(String value) {
                    this.value = value;
                }

                public String getValue() {
                        return value;
                }
	};
    
    private String accessToken;
    private String refreshToken;
    private Long clientId;
    private String clientSecret;
    private AsyncHttpClient http;
    /** news **/
    private Long   expiresIn;
    private String scope;
    private String userId;
    private String tokenType;



    {
        AsyncHttpClientConfig cf = new AsyncHttpClientConfig.Builder()
                 .setUserAgent("MELI-JAVA-SDK-0.0.4").build();
        http = new AsyncHttpClient(cf);
    } 
    
    public AsyncHttpClient http(){
    	return this.http;
    }
    
    public Meli(Long clientId, String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
    }

    public Meli(Long clientId, String clientSecret, String accessToken) {
            this.accessToken = accessToken;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
    }

    public Meli(Long clientId, String clientSecret, String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.refreshToken = refreshToken;
    }
    
    public void initialize(Long clientId, String clientSecret, String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;	
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }
    /** news **/
    public Long getExpiresIn() {
        return this.expiresIn;
    }

    public String getScope() {
        return this.scope;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    @Override
    public Response get(String path) throws MeliException {
        return get(path, new HashMap<String,String>());
    }

    private BoundRequestBuilder prepareGet(String path, Map<String,String> params) {
    		FluentStringsMap paramsGet = new FluentStringsMap();
    		
    		params.forEach((clave,valor)->	paramsGet.add(clave, valor));
    		
            return http.prepareGet(apiUrl + path)
                    .addHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .setQueryParams(paramsGet).setBodyEncoding("UTF-8");
    }

/*
	private BoundRequestBuilder prepareDelete(String path,
			FluentStringsMap params) {
		return http.prepareDelete(apiUrl + path)
				.addHeader("Accept", "application/json")
				.setQueryParams(params);
	}
*/
	private BoundRequestBuilder preparePost(String path,
			Map<String,String> params, String body) {
		FluentStringsMap paramsGet = new FluentStringsMap();
		params.forEach((clave,valor)->	paramsGet.add(clave, valor));
		return http.preparePost(apiUrl + path)
				.addHeader("Accept", "application/json")
				.setQueryParams(paramsGet)
				.setHeader("Content-Type", "application/json").setBody(body)
				.setBodyEncoding("UTF-8");
	}
	private BoundRequestBuilder preparePost(String path,
			Map<String,String> params, File file) throws IllegalArgumentException, FileNotFoundException {
		FluentStringsMap paramsGet = new FluentStringsMap();
		params.forEach((clave,valor)->	paramsGet.add(clave, valor));
		return http.preparePost(apiUrl + path)
				.setQueryParams(paramsGet)
				.addHeader("content-type", "multipart/form-data") 
				.addBodyPart(new FilePart("fiscal_document",file))
				.setBodyEncoding("UTF-8");
	}

	private BoundRequestBuilder preparePut(String path,
			Map<String,String> params, String body) {
		FluentStringsMap paramsGet = new FluentStringsMap();
		params.forEach((clave,valor)->	paramsGet.add(clave, valor));
		return http.preparePut(apiUrl + path)
				.addHeader("Accept", "application/json")
				.setQueryParams(paramsGet)
				.setHeader("Content-Type", "application/json").setBody(body)
				.setBodyEncoding("UTF-8");
	}

	private BoundRequestBuilder preparePost(String path, FluentStringsMap params) {
		return http.preparePost(apiUrl + path)
				.addHeader("Accept", "application/json")
				.setQueryParams(params);
	}


	@Override
	public Response get(String path, Map<String,String> params) throws MeliException {

		BoundRequestBuilder r = prepareGet(path, params);
		
		Response response;
		try {						
			response = r.execute().get();
		} catch (Exception e) {
			throw new MeliException(e);
		}
		return response;
	}
	@Override
    public void refreshAccessToken() throws AuthorizationFailure {
                FluentStringsMap params = new FluentStringsMap();
                params.add("grant_type", "refresh_token");
                params.add("client_id", String.valueOf(this.clientId));
                params.add("client_secret", this.clientSecret);
                params.add("refresh_token", this.refreshToken);
                try {
                    BoundRequestBuilder req = preparePost("/oauth/token", params);
                    parseToken(req);
                }catch (AuthorizationFailure e1) {
                    System.out.println(e1.getMessage());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
    }

        /**
         * 
         * @param callback: The callback URL. Must be the applications redirect URI 
         * @param authUrl: The authorization URL. Get from Meli.AuthUrls
         * @return the authorization URL
         */
        
    public String getAuthUrl(String callback, AuthUrls authUrl) {
                try {
                    return authUrl.getValue() + "/authorization?response_type=code&client_id="
                            + clientId
                            + "&redirect_uri="
                            + URLEncoder.encode(callback, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    return authUrl+"/authorization?response_type=code&client_id="
                            + clientId + "&redirect_uri=" + callback;
                }
    }

      /*  public void authorize(String code, String redirectUri) throws AuthorizationFailure {
                    FluentStringsMap params = new FluentStringsMap();

                    params.add("grant_type", "authorization_code");
                    params.add("client_id", String.valueOf(clientId));
                    params.add("client_secret", clientSecret);
                    params.add("code", code);
                    params.add("redirect_uri", redirectUri);

                    BoundRequestBuilder r = preparePost("/oauth/token", params);

                    parseToken(r);
        }*/
		
		
    private void parseToken(BoundRequestBuilder r) throws AuthorizationFailure {
    	Response response = null;
                String responseBody = "";
                try {
                        response = r.execute().get();
                        responseBody = response.getResponseBody();
                } catch (InterruptedException e) {
                        throw new AuthorizationFailure(e);
                } catch (ExecutionException e) {
                        throw new AuthorizationFailure(e);
                } catch (IOException e) {
                        throw new AuthorizationFailure(e);
                }

                JsonObject object;

                try {
                        object = JsonParser.parseString(responseBody).getAsJsonObject();
                } catch (JsonSyntaxException e) {
                        throw new AuthorizationFailure(responseBody);
                }

                if (response.getStatusCode() == 200) {

                        this.accessToken = object.get("access_token").getAsString();

                        JsonElement jsonElement = object.get("refresh_token");
                        this.refreshToken = jsonElement != null ? object.get(
                                        "refresh_token").getAsString() : null;
                        /** News **/
                        JsonElement jsonElementExpires = object.get("expires_in");
                        this.expiresIn = jsonElementExpires != null ? Long.parseLong(object.get(
                                        "expires_in").getAsString()): null;

                        JsonElement jsonElementScope = object.get("scope");
                        this.scope = jsonElementScope != null ? object.get(
                                        "scope").getAsString() : null;

                        JsonElement jsonElementUserID = object.get("user_id");
                        this.userId = jsonElementUserID != null ? object.get(
                                        "user_id").getAsString() : null;

                        JsonElement jsonElementToken = object.get("token_type");
                        this.tokenType = jsonElementToken != null ? object.get(
                                        "token_type").getAsString() : null;

                } else {
                        throw new AuthorizationFailure(object.get("message").getAsString());
                }

        }

   	public boolean hasRefreshToken() {
            return this.refreshToken != null && !this.refreshToken.isEmpty();
        }

   	@Override
	public Response post(String path, Map<String,String> params, String body) throws MeliException {

		BoundRequestBuilder r = preparePost(path, params, body);

		Response response;
		try {
			response = r.execute().get();
		} catch (Exception e) {
			throw new MeliException(e);
		}

		
		return response;
	}
   	
   	@Override
	public Response postFile(String idOrder, String fullFileName) throws MeliException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token",this.getAccessToken());
		BoundRequestBuilder r =null;
		try {
			r = preparePost("/packs/"+idOrder+"/fiscal_documents",params,new File(fullFileName));
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Response response;
		
		try {
			response = r.execute().get();
		} catch (Exception e) {
			throw new MeliException(e);
		}
		return response;
	}
   	
   	@Override
	public Response put(String path, Map<String,String> params, String body) throws MeliException {

		BoundRequestBuilder r = preparePut(path, params, body);

		Response response;
		try {
			response = r.execute().get();
		} catch (Exception e) {
			throw new MeliException(e);
		}
		
		return response;
	}

/*	public Response delete(String path, FluentStringsMap params) throws MeliException {
		BoundRequestBuilder r = prepareDelete(path, params);

		com.ning.http.client.Response response;
		try {
			response = r.execute().get();
		} catch (Exception e) {
			throw new MeliException(e);
		}
		
		return response;
	}*/

        public BoundRequestBuilder head(String path) {
            return null;
        }

        public BoundRequestBuilder options(String path) {
            return null;
        }

		public void close() {
			try{
				if (this.http != null){
					this.http.close();					
				}
			}
			catch(Exception e){				
			}			
		}

		
}

