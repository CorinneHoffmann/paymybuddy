package com.paymybuddy.paymybuddy.controller;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.paymybuddy.exception.ControllerException;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.services.MainService;

@RestController
@RequestMapping
public class MainController {

	Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	MainService mainService;
	
	@PostMapping(value = "/personneInfo")
	public  ResponseEntity<String> enregistrerPersonne(@RequestBody PersonneInfo personneInfo) {
		
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
		
		logger.info("DEMANDE_ENREGISTRER_PERSONNE " + personneInfo.getEmail());
		mainService.enregistrerPersonne(personneInfo);		
		return new ResponseEntity("Enregistrement OK",HttpStatus.OK);	
	}

	
	@GetMapping(value = "/personneConnection")
	public  ResponseEntity<String> seConnecter(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "motdepasse", required = false) String motDePasse) {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendu dans l'URL");
		}
		if (motDePasse.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre motdepasse attendu dans l'URL");
		}
		logger.info("DEMANDE_SE_CONNECTER");
		mainService.seConnecter(email,motDePasse);		
		return new ResponseEntity("Connexion OK",HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/personneAjouterAmi")
	public  ResponseEntity<String> AjouterUnAmisASaListe(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "emailami", required = false) String emailAmi) {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendu dans l'URL");
		}
		if (emailAmi.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email ami a ajouter attendu dans l'URL");
		}
		if (emailAmi.contentEquals(email)) {
			throw new ControllerException("Vous ne pouvez pas etre votre ami modifier un email");
		}
		logger.info("DEMANDE_AJOUTER_UN_AMI");
		mainService.AjouterUnAmisASaListe(email,emailAmi);		
		return new ResponseEntity("Ajout ami OK",HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/crediterCompte")
	public  ResponseEntity<String> verserMontantSurCompte(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "montant", required = false) Double montant){
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendu dans l'URL");
		}
		if (montant == 0.00) {
			throw new ControllerException("Vous devez saisir un montant différent de 0.00");
		}
		
		logger.info("DEMANDE_CREDITER_UN_MONTANT_SUR_COMPTE");
		mainService.verserMontantSurCompte(email, montant);		
		return new ResponseEntity("versement OK",HttpStatus.OK);
	}
	
	@PostMapping(value = "/payerAmi")
	public  ResponseEntity<String> payerUnAmi(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "emailami", required = false) String emailAmi, 
									 @RequestParam(value = "montant", required = false) Double montant) {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendu dans l'URL");
		}
		if (emailAmi.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre emailami attendu dans l'URL");
		}
		if (montant <= 0.00) {
			throw new ControllerException("Vous devez saisir un montant supérieur à 0");
		}		
		
		logger.info("DEMANDE_PAYER_UN_MONTANT_A_UN_AMI");
		mainService.payerUnAmi(email, emailAmi, montant);		
		return new ResponseEntity("paiement OK",HttpStatus.OK);
	}
	
	@PostMapping(value = "/virementCompteBancaire")
	public  ResponseEntity<String> virerSurCompteBancaire(@RequestParam(value = "email", required = false) String email,
									 @RequestParam(value = "montant", required = false) Double montant)  {
		if (email.isEmpty()) {
			throw new ControllerException("Vous devez saisir le paramètre email attendu dans l'URL");
		}	
		if (montant <= 0.00) {
			throw new ControllerException("Vous devez saisir un montant supérieur à 0");
		}		
		
		logger.info("DEMANDE_VIREMENT_MONTANT_SUR_COMPTE_BANCAIRE");
		mainService.virerSurCompteBancaire(email, montant);		
		return new ResponseEntity("virement OK",HttpStatus.OK);
	}
}
