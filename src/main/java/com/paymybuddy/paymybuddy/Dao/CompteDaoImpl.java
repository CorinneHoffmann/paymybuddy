package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.repository.CompteRepository;

@Repository
public class CompteDaoImpl implements CompteDao {
	
	Logger logger = LoggerFactory.getLogger(PersonneDaoImpl.class);

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CompteRepository compteRepository;

	private long personneId;
	private Compte compte;
	private Personne personne;

	private Double solde;

	@Override
	public Compte findCompteByPersonneId(long personneId) throws NoResultException {
		this.personneId = personneId;
		compte = new Compte();;
		
		try {
			compte = (Compte) entityManager
					.createNativeQuery("SELECT * FROM compte where personneid = :personneId", Compte.class)
					.setParameter("personneId", personneId).getSingleResult();
		} catch (NoResultException e) {
			logger.info("idCompte non trouve", e);
			return null;
		}
		return compte;
	
	}

	@Override
	public void creerCompte(Personne personne, Double solde) {
		this.personne = personne;
		this.solde = solde;
		compte = new Compte();
		
		compte.setSolde(0.00);
		compte.setPersonne(personne);
		compteRepository.save(compte);
		
	}

}
