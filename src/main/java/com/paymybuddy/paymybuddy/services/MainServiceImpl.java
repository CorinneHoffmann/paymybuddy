package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddy.Dao.CompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.OperationCompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.PersonneDaoImpl;
import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.exception.ServiceAddAmiException;
import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.exception.SoldeNegatifException;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.OperationCompte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.CompteRepository;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;

@Service
public class MainServiceImpl implements MainService {

	Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

	@Autowired
	private PersonneRepository personneRepository;
	
	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private PersonneDaoImpl personneDaoImpl;
	
	@Autowired
	private CompteDaoImpl compteDaoImpl;
	
	//@Autowired
	//private OperationCompteRepository operationCompteRepository;
	
	@Autowired 
	OperationCompteDaoImpl operationCompteDaoImpl;

	private String email;
	private PersonneInfo personneInfo;
	private String motDePasse;
	private Personne personne;
	private Compte compte;
	private OperationCompte operationCompte;
	private Personne ami;
	private String emailami;

	private Double montant;

	private SensComptable sensComptable;

	@Transactional
	@Override
	/**
	 * @param personneIfo  information permettant de créer la personne
	 * Enregistrer une personne : 
	 *    verifier si la personne existe (email) 
	 *    creer la personne en base 
	 *    créer un compte associé en base
	 */
	public void enregistrerPersonne(PersonneInfo personneInfo) throws NoResultException{
		this.personneInfo = personneInfo;
		email = personneInfo.getEmail();
	
		if (personneDaoImpl.findByEmail(email) != null) {
			logger.error("RESPONSE_EMAIL_ALREADY_EXISTS " + personneInfo.getEmail());
			throw new ServiceEmailException("Email deja utilise pour enregistrement");
		}
		personne = new Personne();
		personne = personneDaoImpl.creerPersonne(personneInfo);		
		Double solde = 0.00;
		compteDaoImpl.creerCompte(personne, solde);
		
		logger.info("REPONSE_ENREGISTRER_PERSONNE_PERSONNE_CREEE " + personne.getEmail() + "IdPersonne : " + personne.getIdPersonne());
		logger.info("REPONSE_ENREGISTRER_PERSONNE_COMPTE_CREE ");
	}

	/**
	 * @param email - email de la personne qui se connecte
	 * @param motDePasse - motDePasse de la personne qui se connecte
	 */
	@Override
	public Personne seConnecter(String email, String motDePasse) throws NoResultException{
		this.email = email;
		this.motDePasse = motDePasse;
		
		personne = personneDaoImpl.findByEmailAndPassword(email,motDePasse);
		if (personne == null) {
			logger.error("RESPONSE_SE_CONNECTER_KO " + email);
			throw new ServiceConnectionException("Email et/ou mot de passe incorrect");
		}
		logger.info("REPONSE_SE_CONNECTER_OK ");
		return personne;
	}
	/**
	 * @param email email de la personne qui se connecte
	 * @param emailami email de la peronne que l'on veut ajouter à ses amis pour paiement
	 * 
	 * Recherche de l'ami dans la base  partir de son email. Si trouvé alors recherche dans la liste d'amis pour savoir si déjà amis
	 * si non ajout de l'ami à la liste des amis (paiement possible)
	 */

	@Override
	public void AjouterUnAmisASaListe(String email, String emailami) {
		this.email = email;
		this.emailami = emailami;
		boolean findAmiInListe = false;
			
		ami = personneDaoImpl.findByEmail(emailami);	
		if (ami == null) {
			logger.error("RESPONSE_EMAIL_NOT_EXISTS " + emailami);
			throw new ServiceEmailException("Email inexistant en base");
		}else {			
			personne = personneDaoImpl.findByEmail(email);
			int index;			
			for(index=0;index<personne.getAmis().size();index++){
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
		logger.info("REPONSE_AJOUTER_UN_AMI_A_SA_LISTE_OK " + ami.getEmail());
		
	}
	
	

	@Transactional
	@Override
	/**
	 * @param email - email de la personne qui se connecte
	 * @param montant - montant a créditer sur le compte
	 * Enregistrer une operation comptable  et crediter le compte
	 * la personne existe, le compte existe 
	 * pas de bénéficiaire et pas de compte bancaire
	 */
	public void verserMontantSurCompte(String email,  Double montant) {
		
		this.email = email;
		this.montant = montant;
		
		personne = personneDaoImpl.findByEmail(email);
		compte = compteDaoImpl.findCompteByPersonneId(personne.getIdPersonne());
		
		SensComptable credit = SensComptable.C;
		TypeOperation versement = TypeOperation.VERSEMENT;
		
		Double soldeCompte = calculerSoldeCompte(compte,montant,credit);
		
		if (soldeCompte >= 0.00)
		{	
		operationCompteDaoImpl.enregistrerOperationComptable(compte, credit, versement, montant, null, null);
		compte.setSolde(soldeCompte);		
		compteRepository.save(compte);	
		logger.info("REPONSE_COMPTE_CREDITER DE " + soldeCompte);
		}
		else {
			throw new SoldeNegatifException("Solde négatif, operation interdite");
		}

	}

	@Override
	public Double calculerSoldeCompte(Compte compte, Double montant, SensComptable sensComptable) {
		this.compte = compte;
		this.montant = montant;
		this.sensComptable = sensComptable;
		Double solde = 0.00;
		
		if (sensComptable == SensComptable.D)
		{
		  solde = compte.getSolde() - montant;
		}else {
			solde = compte.getSolde() + montant;
		}
				
		return solde;
	}

	/*@Override
	public void virerMontantVersCompteBancaire(String email, Double montant) {
		
		
	}*/
}
	
