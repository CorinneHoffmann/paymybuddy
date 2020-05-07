package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddy.exception.ServiceAddAmiException;
import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.CompteRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepositoryAddMethods;

@Service
public class MainServiceImpl implements MainService {

	Logger logger = LoggerFactory.getLogger(PersonneServiceImpl.class);

	@Autowired
	private PersonneRepository personneRepository;
	
	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private PersonneRepositoryAddMethods personneRepositoryAddMethods;

	private String email;
	private PersonneInfo personneInfo;
	private String motDePasse;
	private Personne personne;
	private Compte compte;
	private Personne ami;

	private String emailajoute;

	@Transactional
	@Override
	/*
	 * Enregistrer une personne - verifier si la personne existe (email) - enregistrer la personne - enregistrer un compte
	 */
	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException{
		this.personneInfo = personneInfo;
		email = personneInfo.getEmail();
		personne = new Personne();
		if (personneRepositoryAddMethods.findByEmail(email) != null) {
			logger.error("RESPONSE_EMAIL_ALREADY_EXISTS " + personneInfo.getEmail());
			throw new ServiceEmailException("Email deja utilise pour enregistrement");
		}
		personne.setEmail(personneInfo.getEmail());
		personne.setMotDePasse(personneInfo.getMotDePasse());
		personne.setNom(personneInfo.getNom());
		personne.setDescription(personneInfo.getDescription());
		personneRepository.save(personne);
		System.out.println("identifiant personne créée " + personne.getIdPersonne());
		
		
		logger.info("REPONSE_ENREGISTRER_PERSONNE_PERSONNE_CREEE " + personne.getEmail() + "IdPersonne : " + personne.getIdPersonne());
		compte = new Compte();
		compte.setSolde(0.00);
		compte.setPersonne(personne);
		compteRepository.save(compte);
		logger.info("REPONSE_ENREGISTRER_PERSONNE_COMPTE_CREE ");
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
	public void AjouterUnAmisASaListe(String email, String emailami) {
		this.email = email;
		this.motDePasse = motDePasse;
		this.emailajoute = emailajoute;
		boolean findAmiInListe = false;
		
		// Recherche de email a jouter dans la base
		ami = personneRepositoryAddMethods.findByEmail(emailami);
		//Si email non trouve alors n remonte le message
		if (ami == null) {
			logger.error("RESPONSE_EMAIL_NOT_EXISTS " + emailami);
			throw new ServiceEmailException("Email inexistant en base");
		//sinon on recherche dans la list si on a deja une personne avec l'email
		}else {
			//Je recupere la personne à patir email (evidemment en temps normal cette etape ne se fait pas
			System.out.println("ami " +ami.getEmail() + " " + ami.getIdPersonne());
			personne = personneRepositoryAddMethods.findByEmail(email);
			System.out.println("personne  " +personne.getEmail() + " " + personne.getIdPersonne());
			System.out.println(" et nombre amis " +personne.getAmis().size());
			int index;
			
			for(index=0;index<personne.getAmis().size();index++){
				System.out.println("lecture des amis boucle numero " +index);
				System.out.println("idpersonne de list personne " +personne.getAmis().get(index).getIdPersonne());
				System.out.println("idpersonne de ami a ajouter " +ami.getIdPersonne());
				if (personne.getAmis().get(index).getIdPersonne() == ami.getIdPersonne()) {
					findAmiInListe = true;
				}
			}
		}
		if (findAmiInListe) {
			logger.error("RESPONSE_CETTE_PERSONNE_EST_DEJA_AMI Id: " + ami.getIdPersonne() +"email " +ami.getEmail());
			throw new ServiceAddAmiException("vous etes deja ami");
		}
			
		personne.getAmis().add(ami);
		personneRepository.save(personne);		
	
	}

}
	
