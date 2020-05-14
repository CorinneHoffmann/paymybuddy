package com.paymybuddy.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.Dao.CompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.PersonneDao;
import com.paymybuddy.paymybuddy.Dao.PersonneDaoImpl;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.CompteRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class MainServiceITTest {

	
	@Autowired
	PersonneDaoImpl personneDaoImpl;
	

	@Autowired
	CompteDaoImpl compteDaoImpl;


	@Autowired
	PersonneRepository personneRepository;
	
	@Autowired
	CompteRepository CompteRepository;
	
	@Autowired
	MainServiceImpl mainServiceImpl;

	@Transactional
	@Test
	void enregistrerPersonneTest() {
		PersonneInfo personneInfo = new PersonneInfo();
		personneInfo.setEmail("creationemail@gmail.com");
		personneInfo.setMotDePasse("motDePasse");
		personneInfo.setNom("creationNom");		
		personneInfo.setDescription("creationDescription");
		
		mainServiceImpl.enregistrerPersonne(personneInfo);
		List<Personne> personnes = personneRepository.findAll();
		assertEquals("creationemail@gmail.com", personnes.get(5).getEmail());
		assertEquals(6, personnes.size());
	}
	
	
	@Test
	void connecterTest() {
		String email = "testmathiasdupont.@yahoo.fr";
		String motDePasse = "testmathias123";
		Personne personne = new Personne();		
		personne = mainServiceImpl.seConnecter(email, motDePasse);
		assertEquals("testdupont", personne.getNom());
	}
	
	@Transactional
	@Test
	void AjouterAmiTest() {
		String email = "testcorinne93.@gmail.com";
		String emailami = "testmathiasdupont.@yahoo.fr";
		mainServiceImpl.ajouterUnAmisASaListe(email, emailami);
		Personne personne = personneDaoImpl.findByEmail("testcorinne93.@gmail.com");
		assertEquals(2, personne.getAmis().size());
	}
	
	
	@Transactional
	@Test
	void verserMontantSurCompteTest() {
		String email = "testcorinne93.@gmail.com";
		Double montant = 200.10;
		mainServiceImpl.verserMontantSurCompte(email, montant);
		Compte compte = compteDaoImpl.findCompteByPersonneId(2);
		assertTrue(compte.getSolde() == 1731.35);
	}
	
	@Transactional
	@Test
	void payerUnAmiTest() {
		String email = "testcorinne93.@gmail.com";
		String emailAmi = "testtitidavant.@gmail.com";
		Double montant = 12.00;
		mainServiceImpl.payerUnAmi(email, emailAmi, montant);
		Compte compte = compteDaoImpl.findCompteByPersonneId(2);
		assertTrue(compte.getSolde() == 1519.19);
	}
	
	@Transactional
	@Test
	void faireUnVirementTest() {
		String email = "testcorinne93.@gmail.com";
		Double montant = 100.00;
		mainServiceImpl.virerSurCompteBancaire(email, montant);
		Compte compte = compteDaoImpl.findCompteByPersonneId(2);
		assertTrue(compte.getSolde() == 1431.25);
	}
	
	
}

