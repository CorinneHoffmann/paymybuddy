package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class PersonneDaoTest {

	@Autowired
	PersonneDaoImpl personneDaoImpl;
	
	@Autowired
	PersonneRepository personneRepository;
	
	
	@Test
	void whenFindPersonneByEmailWhichExists() throws NoResultException, NotSupportedException, SystemException {
		
		Personne personne = new Personne();
		personne = personneDaoImpl.findByEmail("testcorinne93.@gmail.com");
		assertEquals("testalicantour", personne.getNom());
		
	}
	
	@Test
	void whenFindPersonneByEmailWhichNotExists() throws NoResultException, NotSupportedException, SystemException  {
		assertEquals(null, personneDaoImpl.findByEmail("corinne93.@gmail.com"));
	}
	
	@Test
	void whenFindPersonneByEmailAndPasswordWhichExists() throws NoResultException, NotSupportedException, SystemException  {
		Personne personne = new Personne();
		personne = personneDaoImpl.findByEmailAndPassword("testcorinne93.@gmail.com","testcoco93");
		assertEquals("testalicantour", personne.getNom());
	}
	
	@Test
	void whenFindPersonneByEmailAndPasswordWhichNotExists() throws NoResultException, NotSupportedException, SystemException {
		assertEquals(null, personneDaoImpl.findByEmailAndPassword("testcorinne93.@gmail.com","coco92"));
	}
	
	@Transactional
	@Test
	void whentryInsertPersonne() {
		try {
			Personne personne = new Personne();
			personne.setEmail("nouvelinscrit@gmail.com");
			personne.setMotDePasse("nouvelinscrit123");
			personne.setNom("nouvelibscrit nom");
			personne.setDescription("nouvelibscrit description");
			personneRepository.save(personne);
		} catch (Exception e) {
		} finally {
			List<Personne> personnes = personneRepository.findAll();
			assertEquals(6, personnes.size());
		}
	}	
}
