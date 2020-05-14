package com.paymybuddy.paymybuddy.services;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddy.Dao.CommissionDaoImpl;
import com.paymybuddy.paymybuddy.Dao.CompteBancaireDaoImpl;
import com.paymybuddy.paymybuddy.Dao.CompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.OperationCompteDaoImpl;
import com.paymybuddy.paymybuddy.Dao.PersonneDaoImpl;
import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TauxCommission;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.paymybuddy.paymybuddy.exception.ServiceAddAmiException;
import com.paymybuddy.paymybuddy.exception.ServiceAmiException;
import com.paymybuddy.paymybuddy.exception.ServiceCompteBancaireException;
import com.paymybuddy.paymybuddy.exception.ServiceConnectionException;
import com.paymybuddy.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.paymybuddy.exception.SoldeNegatifException;
import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
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
	
	@Autowired
	private CommissionDaoImpl commissionDaoImpl;
	
	@Autowired
	private CompteBancaireDaoImpl compteBancaireDaoImpl;
	
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
	private String emailAmi;
	private Double montant;
	private Compte compteAmi;
	private CompteBancaire compteBancaire;


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
	public void enregistrerPersonne(PersonneInfo personneInfo) {
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
		
		logger.info("REPONSE_ENREGISTRER_PERSONNE_CREEE " + personne.getEmail() + "IdPersonne : " + personne.getIdPersonne());
		logger.info("REPONSE_ENREGISTRER_COMPTE_CREE ");
	}

	/**
	 * @param email - email de la personne qui se connecte
	 * @param motDePasse - motDePasse de la personne qui se connecte
	 */
	@Override
	public Personne seConnecter(String email, String motDePasse){
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
	public void ajouterUnAmisASaListe(String email, String emailAmi) {
		this.email = email;
		this.emailAmi = emailAmi;
		boolean findAmiInListe = false;
			
		ami = personneDaoImpl.findByEmail(emailAmi);	
		if (ami == null) {
			logger.error("RESPONSE_EMAIL_NOT_EXISTS " + emailAmi);
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
		personne = new Personne();
		compte = new Compte();
		
		personne = personneDaoImpl.findByEmail(email);
		compte = compteDaoImpl.findCompteByPersonneId(personne.getIdPersonne());
		
		SensComptable credit = SensComptable.C;
		TypeOperation versement = TypeOperation.VERSEMENT;
		
		Double soldeCompte = calculerSoldeCompte(compte,montant,credit);
		
		if (soldeCompte >= 0.00)
		{	
		operationCompteDaoImpl.creerOperationCompte(compte, credit, versement, montant, null, null);
		compte.setSolde(soldeCompte);		
		compteRepository.save(compte);	
		logger.info("REPONSE_COMPTE_CREDITER DE " + soldeCompte);
		}
		else {
			logger.error("RESPONSE_SOLDE_NEGATIF_VERSEMENT_IMPOSSIBLE ");
			throw new SoldeNegatifException("Solde négatif, operation interdite");
		}

	}
	
	/**
	 * @param email - email de la personne qui se connecte
	 * @param emailAmi - email de la personne que l'on veut payer
	 * @param montant - montant a payer
	 * vérifier existence emailAmi dans la base
	 * vérifier que ami est bien dans la liste d'ami
	 * récupérer le compte a débiter
	 * calculer le taux de commission
	 * calculer le solde du compte a debiter si le paiement est effectué (montant + commission)
	 * si solde positif
	 * 		creer une operation compte débit sur le compte a débiter
	 * 		mettre à jour le solde du compte a debiter
	 * 		creer une operation compte crédit sur le compte a créditer
	 * 		mettre à jour le solde du compte a créditer
	 */
	@Transactional
	@Override
	public void payerUnAmi(String email, String emailAmi, Double montant) {
		this.email = email;
		this.emailAmi = emailAmi;
		this.montant = montant;
		
		boolean findAmiInListe = false;
		
		ami = new Personne();
		personne = new Personne();
		
		ami = personneDaoImpl.findByEmail(emailAmi);	
		if (ami == null) {
			logger.error("RESPONSE_EMAIL_AMI_INEXISTANT " + emailAmi);
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
		if (findAmiInListe == false) {
			logger.error("RESPONSE_AMI_ABSENT_LISTE_AMI " + emailAmi);
			throw new ServiceAmiException("Cette personne n'est pas dans votre liste d'ami");			
		} else {
			compte = new Compte();
			compte = compteDaoImpl.findCompteByPersonneId(personne.getIdPersonne());
			logger.info("COMPTE_A_DEBITER " + compte.getIdCompte() + "SOLDE " + compte.getSolde());
			
			Double montantCommission = calculerMontantCommission(montant);
			logger.info("MONTANT_COMMISSION " + montantCommission);
			
			Double montantTotalDebit = montantCommission + montant;
			logger.info("MONTANT_TOTAL_DEBIT" + montantTotalDebit);
			
			SensComptable debit = SensComptable.D;
			TypeOperation paiement = TypeOperation.PAIEMENT;
		
			Double soldeCompte = calculerSoldeCompte(compte,montantTotalDebit,debit);
			logger.info("COMPTE_A_CREDITER"  + compte.getIdCompte() + "NOUVEAU_SOLDE_SI_PAIEMENT " + soldeCompte);
		
			if (soldeCompte >= 0.00)
				{	
					operationCompte = new OperationCompte();
					operationCompte = operationCompteDaoImpl.creerOperationCompte(compte, debit, paiement, montant, ami, null);
					commissionDaoImpl.creerCommission(operationCompte, montantCommission,TauxCommission.TAUX1);
					compte.setSolde(soldeCompte);		
					compteRepository.save(compte);	
		
					compteAmi = new Compte();
					compteAmi = compteDaoImpl.findCompteByPersonneId(ami.getIdPersonne());

					SensComptable credit = SensComptable.C;		
					soldeCompte = calculerSoldeCompte(compteAmi,montant,credit);
				
					operationCompteDaoImpl.creerOperationCompte(compteAmi, credit, paiement, montant, personne, null);
					
					compteAmi.setSolde(soldeCompte);		
					compteRepository.save(compteAmi);	
					
					logger.info("REPONSE_COMPTE " + email + "DEBITER DE " +montant);
					logger.info("REPONSE_COMPTE " + emailAmi + "CREDITER DE " +montant);
				} else {
					logger.error("RESPONSE_SOLDE_NEGATIF_PAIEMENT_IMPOSSIBLE ");
					throw new SoldeNegatifException("Solde négatif, operation interdite");
				}
		}
	}
	
	/**
	 * @param email - email de la personne qui se connecte
	 * @param montant - montant a virer sur le compte bancaire
	 * récupérer IdPersonne par email
	 * vérifier existence du compte bancaire dans la base
	 * récupérer le compte a débiter
	 * calculer le solde du compte a debiter si le virement est effectué
	 * si solde positif
	 * 		creer une operation Virement compte débit sur le compte a débiter
	 * 		mettre à jour le solde du compte a debiter
	 */
	@Override
	public void virerSurCompteBancaire(String email, Double montant) {
		
		this.email = email;
		this.montant = montant;
		
		personne = new Personne();
		compte = new Compte();
		personne = personneDaoImpl.findByEmail(email);
		compteBancaire = new CompteBancaire();
		compteBancaire = compteBancaireDaoImpl.findCompteBancaireByPersonneId(personne.getIdPersonne());	
		if (compteBancaire == null) {
			logger.error("REPONSE_COMPTE_BANCAIRE_INEXISTANT_EN_BASE_POUR " + email);
			throw new ServiceCompteBancaireException("Compte bancaire inexistant en base");
		}else {		
			
			compte = compteDaoImpl.findCompteByPersonneId(personne.getIdPersonne());			
			SensComptable debit = SensComptable.D;
			TypeOperation virement = TypeOperation.VIREMENT;
			
			Double soldeCompte = calculerSoldeCompte(compte,montant,debit);
			
			if (soldeCompte >= 0.00)
			{	
			operationCompteDaoImpl.creerOperationCompte(compte, debit, virement, montant, null, compteBancaire);
			compte.setSolde(soldeCompte);		
			compteRepository.save(compte);	
			logger.info("REPONSE_COMPTE_DEBITER_DE " + soldeCompte + " POUR VIREMENT BANCAIRE");
			}
			else {
				logger.error("RESPONSE_SOLDE_NEGATIF_PAIEMENT_IMPOSSIBLE ");
				throw new SoldeNegatifException("Solde négatif, operation interdite");
			}
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

	@Override
	public Double calculerMontantCommission(Double montant) {
		this.montant = montant;
		Double montantCommission = 0.00;
		Double tauxCommission = TauxCommission.TAUX1;
		montantCommission = montant*tauxCommission/100;
		return montantCommission;
	}

	

}
	
