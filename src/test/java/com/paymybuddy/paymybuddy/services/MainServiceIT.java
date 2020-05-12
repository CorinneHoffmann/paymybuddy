package com.paymybuddy.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
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
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MainServiceIT {

	
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
	
	@Test
	void AjouterAmiTest() {
		String email = "testcorinne93.@gmail.com";
		String emailami = "testmathiasdupont.@yahoo.fr";
		mainServiceImpl.AjouterUnAmisASaListe(email, emailami);
		Personne personne = personneDaoImpl.findByEmail("testcorinne93.@gmail.com");
		assertEquals(2, personne.getAmis().size());
	}
	
	@Test
	void verserMontantSurCompteTest() {
		String email = "testcorinne93.@gmail.com";
		Double montant = 200.10;
		mainServiceImpl.verserMontantSurCompte(email, montant);
		Compte compte = compteDaoImpl.findCompteByPersonneId(2);
		assertEquals(1731.35, compte.getSolde());
	}
	
	
}

