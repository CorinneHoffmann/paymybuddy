package com.paymybuddy.paymybuddy.Dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
import com.paymybuddy.paymybuddy.model.OperationCompte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.OperationCompteRepository;

@Repository
public class OperationCompteDaoImpl implements OperationCompteDao {
	

@Autowired
OperationCompteRepository operationCompteRepository;


	private Compte compte;
	private SensComptable sensComptable;
	private TypeOperation typeOperation;
	private Double montant;
	private Personne personne;
	private CompteBancaire compteBancaire;
	private OperationCompte operationCompte;

	@Override
	public OperationCompte creerOperationCompte(Compte compte, SensComptable sensComptable, TypeOperation typeOperation,
			Double montant, Personne personne, CompteBancaire compteBancaire) {
		this.compte = compte;
		this.sensComptable = sensComptable;
		this.typeOperation = typeOperation;
		this.montant = montant;
		this.personne = personne;
		this.compteBancaire = compteBancaire;
		
		Date dateOperation = new Date();
		operationCompte = new OperationCompte();
		
		operationCompte.setDebitCredit(sensComptable);
		operationCompte.setTypeOperation(typeOperation);
		operationCompte.setDateOperation(dateOperation);
		operationCompte.setCompte(compte);
		operationCompte.setMontant(montant);	
		operationCompte.setCompteBancaire(compteBancaire);
		operationCompte.setPersonne(personne);
		operationCompteRepository.save(operationCompte);
		System.out.println("operationCompte Id crée " + operationCompte.getIdOperationCompte());
		return operationCompte;
	}

}
