package com.paymybuddy.paymybuddy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.paymybuddy.paymybuddy.Dao.PersonneDaoImpl;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;
import com.paymybuddy.paymybuddy.services.MainService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MainControllerITTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	MainService mainService;

	@Autowired
	PersonneDaoImpl personneDaoImpl;

	@Autowired
	PersonneRepository personneRepository;
	
	@Transactional
	@Test
	void shouldRecordPersonney() throws Exception {

		String json = "{ \"email\":\"NewRecordEmail@gmail.com\", \"motDePasse\":"
					+ "\"NewRecordEmail 123\", \"nom\":\"NewRecordEmail\", \"description\":\"NewRecordEmail\" }";

		MvcResult result = mockmvc.perform(
				post("/personneInfo").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("Enregistrement OK", contentAsString);
	}

	
	@Test
	void shouldConnect() throws Exception {

		
		MvcResult result = mockmvc.perform(get("/personneConnection")
				.param("email", "testcorinne93.@gmail.com")
				.param("motdepasse", "testcoco93")
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("Connexion OK", contentAsString);
	}
	
	@Transactional
	@Test
	void shouldAjouterAmi() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/personneAjouterAmi")
				.param("email", "testcorinne93.@gmail.com")
				.param("emailami", "testmathiasdupont.@yahoo.fr")
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("Ajout ami OK", contentAsString);
	}
	
	@Transactional
	@Test
	void shouldCrediterrCompte() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/crediterCompte")
				.param("email", "testcorinne93.@gmail.com")
				.param("montant", "500.00")
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("versement OK", contentAsString);
	}
	
	@Transactional
	@Test
	void shouldPayerUnAmi() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/payerAmi")
				.param("email", "testcorinne93.@gmail.com")
				.param("emailami", "testtitidavant.@gmail.com")
				.param("montant", "500.00")
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("paiement OK", contentAsString);
	}
	
	@Transactional
	@Test
	void shouldPayerUnAmiKo() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/payerAmi")
				.param("email", "testcorinne93.@gmail.com")
				.param("emailami", "testtitidavant.@gmail.com")
				.param("montant", "1520.00")
				.characterEncoding("utf-8"))
				.andExpect(status().isUnauthorized()).andDo(print()).andReturn();
		
	}
	
	@Transactional
	@Test
	void shouldFaireUnVirementCompteBancaire() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/virementCompteBancaire")
				.param("email", "testcorinne93.@gmail.com")
				.param("montant", "1000.00")
				.characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals("virement OK", contentAsString);		
	}
	
	@Transactional
	@Test
	void shouldFaireUnVirementCompteBancaireKo() throws Exception {

		
		MvcResult result = mockmvc.perform(post("/virementCompteBancaire")
				.param("email", "testchristineleglo.@yahoo.fr")
				.param("montant", "100.00")
				.characterEncoding("utf-8"))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
		
	}
}
