package com.paymybuddy.paymybuddy.controller;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.paymybuddy.exception.ControllerException;
import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.services.MainService;
import com.paymybuddy.paymybuddy.services.PersonneService;

@RestController
@RequestMapping
public class MainController {

	Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	MainService mainService;
	
	@PostMapping(value = "/personneInfo")
	public  ResponseEntity<String> enregistrerPersonne(@RequestBody PersonneInfo personneInfo) throws NoResultException {
		
		if (personneInfo.getEmail().isEmpty()) {
			throw new ControllerException("Vous devez saisir un email");
		}		
		if (personneInfo.getMotDePasse().isEmpty()) {
			throw new ControllerException("Vous devez saisir un mot de passe");
		}
		if (personneInfo.getNom().isEmpty()) {
			throw new ControllerException("Vous devez saisir un nom");
		}
		if (personneInfo.getDescription().isEmpty()) {
			throw new ControllerException("Vous devez saisir une description");
		}
		
		logger.info("QUERY_ENREGISTRER_PERSONNE " + personneInfo.getEmail());
		mainService.enregistrerPersonne(personneInfo);		
		return new ResponseEntity("Enregistrement OK",HttpStatus.OK);	
	}

	
	@GetMapping(value = "/personneConnection")
	public  ResponseEntity<String> seConnecter(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "motdepasse", required = false) String motDePasse) throws NoResultException {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendus dans l'URL");
		}
		if (motDePasse.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre motdepasse attendus dans l'URL");
		}
		logger.info("QUERY_SE_CONNECTER");
		mainService.seConnecter(email,motDePasse);		
		return new ResponseEntity("Connexion OK",HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/personneAjouterAmi")
	public  ResponseEntity<String> AjouterUnAmisASaListe(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "emailami", required = false) String emailami) throws NoResultException {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendus dans l'URL");
		}
		if (emailami.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email ami a ajouter attendus dans l'URL");
		}
		if (emailami.contentEquals(email)) {
			throw new ControllerException("Vous ne pouvez pas etre votre ami modifier un email");
		}
		logger.info("QUERY_AJOUTER_UN_AMI");
		mainService.AjouterUnAmisASaListe(email,emailami);		
		return new ResponseEntity("Ajout ami OK",HttpStatus.OK);
	}
}
