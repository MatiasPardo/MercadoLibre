package org.Mercadolibre;

import java.util.*;
import com.mercadolibre.sdk.*;

public interface ConnectionResource {
	
	public Object get(String path) throws MeliException;
	
	public Object get(String path, Map<String,String> params) throws MeliException;

    public void refreshAccessToken() throws AuthorizationFailure;
    
	public Object post(String path, Map<String,String> params, String body) throws MeliException;
	
	public Object postFile(String idOrder, String fullFileName) throws MeliException;

	public Object put(String path, Map<String,String> params, String body) throws MeliException;


}
