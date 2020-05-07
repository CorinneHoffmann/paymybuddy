package com.paymybuddy.paymybuddy.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;

@Service
public interface PersonneService {

	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException;
	//public void seConnecter(String email, String motDePasse) throws NoResultException;
	public Personne seConnecter(String email, String motDePasse) throws NoResultException;
	public void AjouterUnAmisASaListe(String email, String motDePasse, String emailajoute);
}
