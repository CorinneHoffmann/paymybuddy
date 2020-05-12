package com.paymybuddy.paymybuddy.Dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Personne;
import com.paymybuddy.paymybuddy.model.PersonneInfo;
import com.paymybuddy.paymybuddy.repository.PersonneRepository;

@Repository
public class PersonneDaoImpl implements PersonneDao {

	Logger logger = LoggerFactory.getLogger(PersonneDaoImpl.class);

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PersonneRepository personneRepository;

	private String email;
	private String motDePasse;

	private PersonneInfo personneInfo;
	private Personne personne;

	public Personne findByEmail(String email) throws NoResultException {
		this.email = email;
		personne = new Personne();
		try {
			personne = (Personne) entityManager
					.createNativeQuery("SELECT * FROM personne where email = :email", Personne.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			logger.info("email non trouve", e);
			return null;
		}
		return personne;
	}

	public Personne findByEmailAndPassword(String email, String motDePasse) throws NoResultException {
		this.email = email;
		this.motDePasse = motDePasse;
		personne = new Personne();
		try {
			personne = (Personne) entityManager
					.createNativeQuery("SELECT * FROM personne where email = ?1 and motdepasse = ?2 ", Personne.class)
					.setParameter(1, email).setParameter(2, motDePasse).getSingleResult();
		} catch (NoResultException e) {
			logger.info("email / mot de passe non trouve", e);
			return null;
		}
		return personne;
	}

	public Long checkMaxIdPersonne() {
		Query query = entityManager.createQuery("SELECT max(P.idPersonne) from Personne P");
		Long personneId = (Long) query.getSingleResult();
		return personneId;
	}

	@Override
	public Personne creerPersonne(PersonneInfo personneInfo) {
		this.personneInfo = personneInfo;
		personne = new Personne();
		
		personne.setEmail(personneInfo.getEmail());
		personne.setMotDePasse(personneInfo.getMotDePasse());
		personne.setNom(personneInfo.getNom());
		personne.setDescription(personneInfo.getDescription());
		personne = personneRepository.save(personne);
		return personne;
		
	}
	
	

}
