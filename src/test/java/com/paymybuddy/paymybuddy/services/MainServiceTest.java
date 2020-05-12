package com.paymybuddy.paymybuddy.services;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
//import org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.Dao.CompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.PersonneDaoImpl;
import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.CompteRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MainServiceTest {

	@InjectMocks
	MainServiceImpl mainServiceImpl;
	
	@Mock
	PersonneRepository personneRepository;
	@Mock
	CompteRepository compteRepository;
	@Mock
	PersonneDaoImpl personneDaoImpl;
	@Mock
	CompteDaoImpl compteDaoImpl;


	@Test
	void whenEnregistrerPersonne() {
	
		Personne personne = new Personne();	
		Compte compte = new Compte();
		Double solde = 0.00;
		compte.setSolde(solde);
		compte.setPersonne(personne);
		Long idcompte = (long) 6;
		compte.setIdCompte(idcompte);
		compteRepository.save(compte);
		personne.setEmail("creationemail@gmail.com");
		personne.setMotDePasse("motDePasse");
		personne.setNom("creationNom");		personne.setDescription("creationDescription");
		Long idpersonne = (long) 6;
		personne.setIdPersonne(idpersonne);
		
		when(personneDaoImpl.findByEmail("creationemail@gmail.com")).thenReturn(null);		
		when(personneRepository.save(any(Personne.class))).thenReturn(personne);
		when(compteRepository.save(any(Compte.class))).thenReturn(compte);

		assertTrue(compte.getSolde()==0.00);
	}
	

	@Test
	void whenCalculateSoldeCredit() {
		Personne personne = new Personne();
		personne.setIdPersonne((long) 1);
		//personne.setEmail("testmathiasdupont.@yahoo.fr");
		//personne.setMotDePasse("testmathias123");
		Compte compte = new Compte();
		compte.setIdCompte((long) 1);
		compte.setSolde(100.50);
		compte.setPersonne(personne);
		Double montant = 200.00;
		SensComptable credit = SensComptable.C;
		
		Double solde = mainServiceImpl.calculerSoldeCompte(compte, montant, credit);
		assertEquals(300.50,solde);
	}
	
	@Test
	void whenCalculateSoldeDebitPositif() {
		Personne personne = new Personne();
		personne.setIdPersonne((long) 1);
		//personne.setEmail("testmathiasdupont.@yahoo.fr");
		//personne.setMotDePasse("testmathias123");
		Compte compte = new Compte();
		compte.setIdCompte((long) 1);
		compte.setSolde(100.50);
		compte.setPersonne(personne);
		Double montant = 100.00;
		SensComptable debit = SensComptable.D;
		
		Double solde = mainServiceImpl.calculerSoldeCompte(compte, montant, debit);
		assertEquals(0.50,solde);
	}
	
	@Test
	void whenCalculateSoldeDebitNegatif() {
		Personne personne = new Personne();
		personne.setIdPersonne((long) 1);
		//personne.setEmail("testmathiasdupont.@yahoo.fr");
		//personne.setMotDePasse("testmathias123");
		Compte compte = new Compte();
		compte.setIdCompte((long) 1);
		compte.setSolde(100.50);
		compte.setPersonne(personne);
		Double montant = 200.00;
		SensComptable debit = SensComptable.D;
		
		Double solde = mainServiceImpl.calculerSoldeCompte(compte, montant, debit);
		assertEquals(-99.50,solde);
	}
	
}
