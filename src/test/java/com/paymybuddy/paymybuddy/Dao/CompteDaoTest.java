package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.CompteRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
		System.out.println("compte " + compte.getSolde());
		assertEquals(1531.25, compte.getSolde());		
	}
	
	@Test
	void whenFindCompteByPersonneIdWhichNotExists() throws NoResultException, NotSupportedException, SystemException  {
		Long personneId = (long) 6;			
		assertEquals(null, compteDaoImpl.findCompteByPersonneId(personneId));
	}
	
@Test
	
	void whentryInsertComptewithExistForeignKeys() {
		try {
			Personne personne = new Personne();
			personne.setEmail("mail.creationcompte@gmail.com");
			personne.setMotDePasse("motDePasse");
			personne.setNom("creationCompte");
			personne.setDescription("creationCompteDescription");
			Long id = (long) 5;
			personne.setIdPersonne(id); 
			Compte compte = new Compte();
			compte.setSolde(200.00);
			compte.setPersonne(personne);
			compteRepository.save(compte);
		} catch (Exception e) {
		} finally {
			List<Compte> comptes = compteRepository.findAll();
			assertEquals(5, comptes.size());
		}
	}
	

}
