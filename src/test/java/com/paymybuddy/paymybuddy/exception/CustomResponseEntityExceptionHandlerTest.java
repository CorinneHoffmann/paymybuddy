package com.paymybuddy.paymybuddy.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.PaymybuddyApplication;

@SpringBootTest(classes = PaymybuddyApplication.class)
@ContextConfiguration
@ActiveProfiles("test")
class CustomResponseEntityExceptionHandlerTest {

	private static CustomResponseEntityExceptionHandler customResponseEntityExceptionHandler;
	private static HttpStatus httpStatus;

	@BeforeAll
	private static void setUp() {
		customResponseEntityExceptionHandler = new CustomResponseEntityExceptionHandler();
	}
	
	@Test
	public void whenhandleFoundExceptionEmail() {
		String message = "objet deja present";
		ServiceEmailException ex = new ServiceEmailException(message);

		assertEquals(HttpStatus.FOUND,
				customResponseEntityExceptionHandler.handleFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleFoundException(ex).getMessage());

	}
	
	@Test
	public void whenHandleNotFoundExceptionCompteBancaire() {
		String message = "objet absent des listes";
		ServiceCompteBancaireException ex = new ServiceCompteBancaireException(message);

		assertEquals(HttpStatus.NOT_FOUND,
				customResponseEntityExceptionHandler.handleNotFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleNotFoundException(ex).getMessage());

	}
	
	@Test
	public void whenhandleFoundExceptionAmi() {
		String message = "ami deja present";
		ServiceAddAmiException ex = new ServiceAddAmiException(message);

		assertEquals(HttpStatus.FOUND,
				customResponseEntityExceptionHandler.handleFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleFoundException(ex).getMessage());

	}
	

	@Test
	public void whenHandleNotFoundExceptionAmi() {
		String message = "ami absent de la base";
		ServiceAmiException ex = new ServiceAmiException(message);

		assertEquals(HttpStatus.NOT_FOUND,
				customResponseEntityExceptionHandler.handleNotFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleNotFoundException(ex).getMessage());

	}
	
	@Test
	public void whenHandleControllerException() {
		String message = "parametres URL absents";
		ControllerException ex = new ControllerException(message);

		assertEquals(HttpStatus.BAD_REQUEST,
				customResponseEntityExceptionHandler.handleControllerException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleControllerException(ex).getMessage());

	}
	
	@Test
	public void whenHandleNotFoundExceptionPersonne() {
		String message = "personne absente en base";
		ServiceConnectionException ex = new ServiceConnectionException(message);

		assertEquals(HttpStatus.NOT_FOUND,
				customResponseEntityExceptionHandler.handleNotFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleNotFoundException(ex).getMessage());

	}
	
	@Test
	public void whenhandleunhautorizedException() {
		String message = "solde negatif";
		SoldeNegatifException ex = new SoldeNegatifException(message);

		assertEquals(HttpStatus.UNAUTHORIZED,
				customResponseEntityExceptionHandler.handleUnauthorizedException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleUnauthorizedException(ex).getMessage());
	}


	@Test
	public void whenHandleRuntimeException() {
		RuntimeException ex = new RuntimeException();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				customResponseEntityExceptionHandler.handleRuntimeException(ex).getHttpStatus());
	}
	
	

	
}
