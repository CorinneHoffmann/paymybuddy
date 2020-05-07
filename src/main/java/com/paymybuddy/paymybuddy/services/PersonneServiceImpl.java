package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepositoryAddMethods;

@Service
public class PersonneServiceImpl implements PersonneService {

	Logger logger = LoggerFactory.getLogger(PersonneServiceImpl.class);

	@Autowired
	private PersonneRepository personneRepository;

	@Autowired
	private PersonneRepositoryAddMethods personneRepositoryAddMethods;

	private String email;
	private PersonneInfo personneInfo;
	private String motDePasse;
	private Personne personne = new Personne();
	private Personne ami = new Personne();

	private String emailajoute;

	@Transactional
	@Override
	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException{
		this.personneInfo = personneInfo;
		email = personneInfo.getEmail();

		if (personneRepositoryAddMethods.findByEmail(email) != null) {
			logger.error("RESPONSE_EMAIL_ALREADY_EXISTS " + personneInfo.getEmail());
			throw new ServiceEmailException("Email deja utilise pour enregistrement");
		}
		personne.setEmail(personneInfo.getEmail());
		personne.setMotDePasse(personneInfo.getMotDePasse());
		personne.setNom(personneInfo.getNom());
		personne.setDescription(personneInfo.getDescription());
		personneRepository.save(personne);
		logger.info("RESPONSE_PERSONNE_CREEE " + personne.getEmail() + "IdPersonne : " + personne.getIdPersonne());
	}

	@Override
	public Personne seConnecter(String email, String motDePasse) throws NoResultException{
		this.email = email;
		this.motDePasse = motDePasse;
		
		personne = personneRepositoryAddMethods.findByEmailAndPassword(email,motDePasse);
		if (personne == null) {
			logger.error("RESPONSE_IDENTIFICATION_NOT_EXISTS " + email);
			throw new ServiceConnectionException("Email et/ou mot de passe incorrect");
		}
		System.out.println("personne recuperée " +personne.getIdPersonne());
		System.out.println("personne recuperée amis " +personne.getAmis().size());
		return personne;
		/*if (personneRepositoryAddMethods.findByEmailAndPassword(email,motDePasse) == null) {
			logger.error("RESPONSE_IDENTIFICATION_NOT_EXISTS " + email);
			throw new ServiceConnectionException("Email et/ou mot de passe incorrect");
		}		*/
	}

	@Override
	public void AjouterUnAmisASaListe(String email, String motDePasse, String emailajoute) {
		this.email = email;
		this.motDePasse = motDePasse;
		this.emailajoute = emailajoute;
		
		// Recherche de email a jouter dans la base
		ami = personneRepositoryAddMethods.findByEmail(email);
		//Si email non trouve alors n remonte le message
		if (ami == null) {
			logger.error("RESPONSE_EMAIL_NOT_EXISTS " + email);
			throw new ServiceEmailException("Email inexistant en base");
		//sinon on recherche dans la list si on a deja une personne avec l'email
		}else {
			//Je recupere la personne à patir email et mot de passe
			personne = personneRepositoryAddMethods.findByEmailAndPassword(email,motDePasse);
			
			System.out.println("nombre amis " +personne.getAmis().size());
			int index;
			for(index=0;index<personne.getAmis().size();index++){
				
			}
			
			
		}
	}

}
