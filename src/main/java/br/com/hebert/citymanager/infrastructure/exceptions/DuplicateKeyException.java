package br.com.hebert.citymanager.infrastructure.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateKeyException extends DataIntegrityViolationException{

	public DuplicateKeyException(String message){
    	super(message);
    }
}
