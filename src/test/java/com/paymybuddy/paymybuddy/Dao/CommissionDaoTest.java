package com.paymybuddy.paymybuddy.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.paymybuddy.paymybuddy.model.Commission;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.OperationCompte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.CommissionRepository;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration
class CommissionDaoTest {

	@Autowired
	CommissionRepository commissionRepository;

	@Transactional
	@Test
	void whentryInsertCommission() {
		
		try {
			Personne personne = new Personne();
			Long idPersonne = (long) 2;
			personne.setIdPersonne(idPersonne);
			
			Compte compte = new Compte();
			Long idCompte = (long) 2;
			compte.setIdCompte(idCompte);
			
			OperationCompte operationCompte = new OperationCompte();
			Long idOperationCompte = (long) 2;
			operationCompte.setIdOperationCompte(idOperationCompte);
			
			Commission commission = new Commission();
			commission.setMontant(3.12);
			commission.setOperationCompte(operationCompte);
			commission.setTaux(2.00);
			commissionRepository.save(commission);
			
		} catch (Exception e) {
		} finally {
			List<Commission> commissions = commissionRepository.findAll();
			assertEquals(2, commissions.size());
		}
	}

}
