package br.com.hebert.citymanager.api.handler;

import javax.servlet.http.HttpServletRequest;

import br.com.hebert.citymanager.infrastructure.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND) // 403
	public @ResponseBody
	ExceptionResponse handleResourceNotFound(final ResourceNotFoundException ex, final HttpServletRequest request) {
		return createExceptionResponse(ex, request) ;
	}
	
    @ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    public @ResponseBody ExceptionResponse handleConflict(final DuplicateKeyException ex, final HttpServletRequest request) {
		return createExceptionResponse(ex, request) ;
    }
    
    @ExceptionHandler(NullValueException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    public @ResponseBody ExceptionResponse handleConflict(final NullValueException ex, final HttpServletRequest request) {
		return createExceptionResponse(ex, request) ;
    }

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
	public @ResponseBody ExceptionResponse handleConflict(final BadRequestException ex, final HttpServletRequest request) {
		return createExceptionResponse(ex, request) ;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500
	public @ResponseBody ExceptionResponse handleException(final Exception ex, final HttpServletRequest request) {
		return createExceptionResponse(ex, request) ;
	}
	
	private ExceptionResponse createExceptionResponse(Exception ex, HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(ex.getLocalizedMessage());
		exceptionResponse.setError(ex.getClass().getCanonicalName());
		exceptionResponse.setPath(request.getRequestURI());
		return exceptionResponse;
		
	}

}
