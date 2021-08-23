package br.com.hebert.citymanager.infrastructure.exceptions;

public class BadRequestException extends Exception {
	public BadRequestException(String message){
    	super(message);
    }
}
