package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

@Repository
public interface PersonneDao {
	
	public Personne findByEmail(String email) throws NoResultException;
	public Personne findByEmailAndPassword(String email, String motDePasse) throws NoResultException;	
	public Personne creerPersonne(PersonneInfo personneInfo);

}
