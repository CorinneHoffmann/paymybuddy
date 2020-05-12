package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	
@Test
	
	void whentryInsertOperationCompteVersement() {
		try {
			Personne personne = new Personne();
			personne.setEmail("corinne93.@gmail.com");
			personne.setMotDePasse("coco93");
			personne.setNom("alicantour");
			personne.setDescription("restauration amigos");
			Long idPersonne = (long) 2;
			personne.setIdPersonne(idPersonne); 
			
			Compte compte = new Compte();
			Long idCompte = (long) 2;
			compte.setIdCompte(idCompte);
			compte.setSolde(1531.25);
			compte.setPersonne(personne);
			
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
			assertEquals(4, operations.size());
		}
	}

}
