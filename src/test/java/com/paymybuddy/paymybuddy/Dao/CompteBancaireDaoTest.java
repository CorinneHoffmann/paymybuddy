package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.NoResultException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
import com.paymybuddy.paymybuddy.repository.CompteBancaireRepository;
import com.paymybuddy.paymybuddy.repository.CompteRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class CompteBancaireDaoTest {


	@Autowired
	CompteBancaireDaoImpl compteBancaireDaoImpl;
	
	@Autowired
	CompteBancaireRepository compteBancaireRepository;
	
	@Test
	void whenFindCompteBancaioreByPersonneIdWhichExists() throws NoResultException, NotSupportedException, SystemException {
		
		CompteBancaire compteBancaire = new CompteBancaire();
		Long personneId = (long) 2;			
		compteBancaire = compteBancaireDaoImpl.findCompteBancaireByPersonneId(personneId);
		assertEquals("FR02 1860 17D1 12KL FGHJ 17", compteBancaire.getIban());		
	}
	
	@Test
	void whenFindCompteByPersonneIdWhichNotExists() throws NoResultException, NotSupportedException, SystemException  {
		Long personneId = (long) 3;			
		assertEquals(null, compteBancaireDaoImpl.findCompteBancaireByPersonneId(personneId));
	}
	

}
