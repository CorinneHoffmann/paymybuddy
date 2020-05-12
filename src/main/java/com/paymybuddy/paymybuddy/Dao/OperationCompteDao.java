package com.paymybuddy.paymybuddy.Dao;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
import com.paymybuddy.paymybuddy.model.Personne;

@Repository
public interface OperationCompteDao {
	
	public void enregistrerOperationComptable(Compte compte, SensComptable sensComptable, TypeOperation typeOperation, Double montant, Personne beneficiaire, CompteBancaire compteBancaire );

}
