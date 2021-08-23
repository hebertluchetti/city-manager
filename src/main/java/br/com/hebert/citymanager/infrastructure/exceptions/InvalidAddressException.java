package br.com.hebert.citymanager.infrastructure.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class InvalidAddressException extends DataIntegrityViolationException{
	private static final long serialVersionUID = 6719481951789137937L;

	public InvalidAddressException(String message){
    	super(message);
    }
}
