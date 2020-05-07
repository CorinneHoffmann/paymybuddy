package com.paymybuddy.paymybuddy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.model.Personne;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class PersonneRepositoryTest {

	@Autowired
	PersonneRepositoryAddMethods personneRepositoryAddMethods;

	@Autowired
	PersonneRepository personneRepository;

	@Test
	void whentryInsertPersonneWithSameEmail1() {
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

	@Test
	void whentryInsertPersonneWithSameEmail2() {
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

	@Test
	void whenFindPersonneByEmailWhichExists() {
		Personne personne = new Personne();
		personne = personneRepositoryAddMethods.findByEmail("testcorinne93.@gmail.com");
		System.out.println(personne.getNom());
		assertEquals("testalicantour", personne.getNom());
	}
	
	@Test
	void whenFindPersonneByEmailWhichNotExists() {
		assertEquals(null, personneRepositoryAddMethods.findByEmail("corinne93.@gmail.com"));
	}
	
	@Test
	void whenFindPersonneByEmailAndPasswordWhichExists() throws NoResultException {
		Personne personne = new Personne();
		personne = personneRepositoryAddMethods.findByEmailAndPassword("testcorinne93.@gmail.com","testcoco93");
		System.out.println(personne.getNom());
		assertEquals("testalicantour", personne.getNom());
	}
	
	@Test
	void whenFindPersonneByEmailAndPasswordWhichNotExists() {
		assertEquals(null, personneRepositoryAddMethods.findByEmailAndPassword("testcorinne93.@gmail.com","coco92"));
	}
	
	

}
