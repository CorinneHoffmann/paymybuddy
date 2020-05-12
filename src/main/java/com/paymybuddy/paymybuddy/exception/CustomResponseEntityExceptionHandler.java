package com.paymybuddy.paymybuddy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paymybuddy.paymybuddy.model.ApiResponse;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

	@ExceptionHandler(ServiceEmailException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FOUND)
	public ApiResponse handleFoundException(ServiceEmailException e) {
		logger.error("email found");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.FOUND, e.getMessage());
		return apiresponse;
	}

	@ExceptionHandler(ControllerException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse handleControllerException(ControllerException ex) {
		logger.error("Information vide");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return apiresponse;
	}
	

	@ExceptionHandler(ServiceConnectionException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleFoundException(ServiceConnectionException e) {
		logger.error("identifiants not found");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(SoldeNegatifException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleFoundException(SoldeNegatifException e) {
		logger.error("identifiants not found");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return apiresponse;
	}


	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse handleRuntimeException(RuntimeException e) {
		logger.error("INTERNAL_SERVER_ERROR");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		return apiresponse;
	}

}
