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
		logger.error("EMAIL_DEJA_EXISTANT");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.FOUND, e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(ServiceCompteBancaireException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleNotFoundException(ServiceCompteBancaireException e) {
		logger.error("COMPTE_BANCAIRE_INEXISTANT");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(ServiceAmiException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleNotFoundException(ServiceAmiException e) {
		logger.error("PERSONNE_NON_REFERENCEE_COMME_AMI");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(ServiceAddAmiException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FOUND)
	public ApiResponse handleFoundException(ServiceAddAmiException e) {
		logger.error("PERSONNE_DEJA_REFERENCEE_COMME_AMI");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.FOUND, e.getMessage());
		return apiresponse;
	}

	@ExceptionHandler(ControllerException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse handleControllerException(ControllerException ex) {
		logger.error("INFORMATION_VIDE");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return apiresponse;
	}
	

	@ExceptionHandler(ServiceConnectionException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleNotFoundException(ServiceConnectionException e) {
		logger.error("IDENTIFICATION_IMPOSSIBLE");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(SoldeNegatifException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiResponse handleUnauthorizedException(SoldeNegatifException e) {
		logger.error("SOLDE_NEGATIF");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
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
