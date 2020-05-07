package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

public interface MainService {
	
	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException;
	public Personne seConnecter(String email, String motDePasse) throws NoResultException;
	public void AjouterUnAmisASaListe(String email, String emailami);

}
