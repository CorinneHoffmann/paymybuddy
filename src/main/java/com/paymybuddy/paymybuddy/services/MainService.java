package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

@Service
public interface MainService {
	
	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException;
	public Personne seConnecter(String email, String motDePasse) throws NoResultException;
	public void AjouterUnAmisASaListe(String email, String emailami);
	public void verserMontantSurCompte(String email,  Double montant);
	public Double calculerSoldeCompte(Compte compte,  Double montant, SensComptable sensComptable);
	
	//public void virerMontantVersCompteBancaire(String email,  Double montant);

}
