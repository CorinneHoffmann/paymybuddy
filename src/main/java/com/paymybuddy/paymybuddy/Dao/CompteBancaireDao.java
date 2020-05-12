package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.CompteBancaire;

@Repository
public interface CompteBancaireDao {
	
	public CompteBancaire findCompteBancaireByPersonneId(long personneId) throws NoResultException;

}
