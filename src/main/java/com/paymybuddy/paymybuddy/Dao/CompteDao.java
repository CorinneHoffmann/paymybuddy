package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

@Repository
public interface CompteDao {
	
	public Compte findCompteByPersonneId(long personneId) throws NoResultException;
	public void creerCompte(Personne personne, Double Solde);

}