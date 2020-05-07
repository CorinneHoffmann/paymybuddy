package com.paymybuddy.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
class MainServiceTest {

	@Autowired
	PersonneRepository personneRepository;

	@Test
	void whenRecordPersonne() {
		List<Personne> personnes = personneRepository.findAll();
		System.out.println(personnes.get(0).getEmail());
		System.out.println("email personne 0 " + personnes.get(0).getEmail());
		assertEquals(5,personnes.size());
	}

}
