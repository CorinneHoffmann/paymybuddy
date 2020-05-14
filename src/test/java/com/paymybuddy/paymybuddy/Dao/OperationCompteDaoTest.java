package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.OperationCompte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.OperationCompteRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class OperationCompteDaoTest {

	@Autowired
	OperationCompteRepository operationCompteRepository;

@Transactional
@Test
	
	void whentryInsertOperationCompteVersement() {
		try {
			Personne personne = new Personne();
			Long idPersonne = (long) 2;
			personne.setIdPersonne(idPersonne); 
			
			Compte compte = new Compte();
			Long idCompte = (long) 2;
			compte.setIdCompte(idCompte);
			
			OperationCompte operationCompte = new OperationCompte();
			SensComptable credit = SensComptable.C;
			TypeOperation versement = TypeOperation.VERSEMENT;
			Double montant = 400.00;
			Date dateOperation = new Date();
			
			operationCompte.setDebitCredit(credit);
			operationCompte.setTypeOperation(versement);
			operationCompte.setMontant(montant);
			operationCompte.setCompte(compte);
			operationCompte.setDateOperation(dateOperation);
			operationCompte.setCompteBancaire(null);
			operationCompte.setPersonne(null);
			operationCompteRepository.save(operationCompte);
		} catch (Exception e) {
		} finally {
			List<OperationCompte> operations = operationCompteRepository.findAll();
			assertEquals(5, operations.size());
		}
	}

}
