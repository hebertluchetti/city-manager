package br.com.hebert.citymanager.infrastructure.exceptions;


public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -2035959153355430666L;

	public ResourceNotFoundException(String message){
    	super(message);
    }
}
