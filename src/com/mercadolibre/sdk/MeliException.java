package com.mercadolibre.sdk;

public class MeliException extends Exception {
	
    public MeliException(Throwable cause) {
    	super(cause);
    }

    public MeliException(String string) {
    	super(string);
	}

	private static final long serialVersionUID = 7263275678852231779L;
}
