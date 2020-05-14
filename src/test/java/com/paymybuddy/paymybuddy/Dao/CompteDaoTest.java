package com.paymybuddy.paymybuddy.Dao;



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.CompteRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class CompteDaoTest {

	
	@Autowired
	CompteDaoImpl compteDaoImpl;
	
	@Autowired
	CompteRepository compteRepository;
	
	@Test
	void whenFindCompteByPersonneIdWhichExists() throws NoResultException, NotSupportedException, SystemException {
		
		Compte compte = new Compte();
		Long personneId = (long) 2;			
		compte = compteDaoImpl.findCompteByPersonneId(personneId);
		assertTrue(compte.getSolde()==1531.25);		
	}
	
	@Test
	void whenFindCompteByPersonneIdWhichNotExists() throws NoResultException, NotSupportedException, SystemException  {
		Long personneId = (long) 6;			
		assertTrue(compteDaoImpl.findCompteByPersonneId(personneId) == null);
	}

@Transactional
@Test
	
	void whentryInsertComptewithExistForeignKeys() {
		try {
			Personne personne = new Personne();
			Long id = (long) 5;
			personne.setIdPersonne(id); 
			Compte compte = new Compte();
			compte.setSolde(200.00);
			compte.setPersonne(personne);
			compteRepository.save(compte);
		} catch (Exception e) {
		} finally {
			List<Compte> comptes = compteRepository.findAll();
			assertTrue(comptes.size() == 5);
		}
	}
	

}
