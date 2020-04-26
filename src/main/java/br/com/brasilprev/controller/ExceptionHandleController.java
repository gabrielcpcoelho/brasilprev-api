package br.com.brasilprev.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.brasilprev.core.exception.ErrorResponse;
import br.com.brasilprev.core.exception.NotAllowedExclusionException;

@RestController
@ControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(NotAllowedExclusionException.class)
	public ErrorResponse handleNotAllowedExclusionException(Exception ex, HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setPath(request.getServletPath());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setError("Not allowed");
		errorResponse.setTimestamp(Calendar.getInstance().getTimeInMillis());
		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());

		return errorResponse;
	}

}
