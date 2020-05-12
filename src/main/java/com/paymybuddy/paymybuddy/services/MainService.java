package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

@Service
public interface MainService {
	
	public void enregistrerPersonne(PersonneInfo personneInfo);
	public Personne seConnecter(String email, String motDePasse);
	public void AjouterUnAmisASaListe(String email, String emailAmi);
	public void verserMontantSurCompte(String email,  Double montant);
	public Double calculerSoldeCompte(Compte compte,  Double montant, SensComptable sensComptable);
	public Double calculerMontantCommission(Double montant);
	public void payerUnAmi(String email, String emailAmi, Double montant);
	public void virerSurCompteBancaire(String email, Double montant);

}
