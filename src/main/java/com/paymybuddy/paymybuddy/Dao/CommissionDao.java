package com.paymybuddy.paymybuddy.Dao;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.OperationCompte;

@Repository
public interface CommissionDao {
	
	public void creerCommission(OperationCompte operationCompte, Double montant, Double taux);

}
