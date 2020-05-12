package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Compte;
import com.paymybuddy.paymybuddy.model.CompteBancaire;
import com.paymybuddy.paymybuddy.repository.CompteBancaireRepository;
import com.paymybuddy.paymybuddy.repository.CompteRepository;

@Repository
public class CompteBancaireDaoImpl implements CompteBancaireDao {

	
	Logger logger = LoggerFactory.getLogger(PersonneDaoImpl.class);

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CompteBancaireRepository compteBancaireRepository;

	private long personneId;
	private CompteBancaire compteBancaire;
	
	@Override
	public CompteBancaire findCompteBancaireByPersonneId(long personneId) throws NoResultException {
		
		this.personneId = personneId;
		compteBancaire = new CompteBancaire();;
		
		try {
			compteBancaire = (CompteBancaire) entityManager
					.createNativeQuery("SELECT * FROM comptebancaire where personneid = :personneId", CompteBancaire.class)
					.setParameter("personneId", personneId).getSingleResult();
		} catch (NoResultException e) {
			logger.info("idCompteBancaire non trouve", e);
			return null;
		}
		return compteBancaire;	
	}

}
