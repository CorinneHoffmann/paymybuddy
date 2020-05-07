package com.paymybuddy.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepositoryAddMethods;
import com.paymybuddy.paymybuddy.services.PersonneService;

@RestController
@RequestMapping
public class PersonneController {

	
	@Autowired
	PersonneService personneService;
	
	@Autowired
	PersonneRepositoryAddMethods personneRepositoryAddMethods;
	
	@Autowired
	PersonneRepository personneRepository;

	@GetMapping(value = "/personnes")
	List<Personne> list() {
		List<Personne> personnes = personneRepository.findAll();
		System.out.println("nb personnes" + personnes.size());
		for(int index = 0;index<personnes.size();index++) {
		System.out.println("nom " + personnes.get(index).getNom());
		}
		return personnes;
	}
	
	
	@GetMapping(value = "/personne/email")
	public Personne findByEmail(@RequestParam(value = "email") String email) {
	System.out.println(email);	
	//List<Personne> personnes = personneServiceImpl.list();
	Personne personne = personneRepositoryAddMethods.findByEmail(email);
	return personne;
	}
	
	@PostMapping(value = "/personnePost")
	public ResponseEntity<String> savePersonne(@RequestBody PersonneInfo personneInfo) {
		Personne personne = new Personne();
		personne.setEmail(personneInfo.getEmail());
		personne.setMotDePasse(personneInfo.getMotDePasse());
		personne.setNom(personneInfo.getNom());
		personne.setDescription(personneInfo.getDescription());
		personneRepository.save(personne);
		System.out.println("personneSave");
		return new ResponseEntity("Enregistrement OK",HttpStatus.OK);
	}



}
