package com.paymybuddy.paymybuddy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Personne;

@Repository
public class PersonneRepositoryAddMethods {

	Logger logger = LoggerFactory.getLogger(PersonneRepositoryAddMethods.class);
	
	
	@Autowired
	private EntityManager entityManager;
	
	private String email;

	private String motDePasse;

	/*@Transactional
	public Personne findByEmail(String email) throws NoResultException {
		this.email=email;
		Personne personne;
		try {
			personne = (Personne) entityManager.createNativeQuery("SELECT * FROM personne where email = ?1",Personne.class)
					.setParameter(1, email)
					.getSingleResult();
		} catch (NoResultException e) {
		logger.info("email non trouve", e);
		return null;
		}
		return personne;
}*/
	
	public Personne findByEmail(String email) throws NoResultException {
		this.email=email;
		Personne personne;
		try {
			personne = (Personne) entityManager.createNativeQuery("SELECT * FROM personne where email = :email",Personne.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
		logger.info("email non trouve", e);
		return null;
		}
		return personne;
}
	
	
	public Personne findByEmailAndPassword(String email, String motDePasse) throws NoResultException  {
		this.email=email;
		this.motDePasse=motDePasse;
		Personne personne;
		try {		
		personne = (Personne) entityManager.createNativeQuery("SELECT * FROM personne where email = ?1 and motdepasse = ?2 ",Personne.class)
				.setParameter(1, email)
				.setParameter(2, motDePasse)
				.getSingleResult();
		}catch (NoResultException e){
			logger.info("email / mot de passe non trouve", e);
			return null;		
		}
		return personne;	
	}

	
	public Long checkMaxIdPersonne() {
			Query query = entityManager.createQuery ("SELECT max(P.idPersonne) from Personne P");
			Long personneId = (Long) query.getSingleResult ();
			return personneId;
	}
	
	/*List<Personne> personnes = entityManager.createNativeQuery("SELECT * FROM personne where email = ?1 and motdepasse = ?2 ",Personne.class).setParameter(1, email).setParameter(2, motDePasse)
			//		.getResultList();
			
			
			 * 	public Personne findByEmail(String email) throws NoResultException {
		this.email=email;
		Personne personne;
		try {
			Query query = entityManager.createQuery ("FROM Personne P WHERE P.email = ?1");
			query.setParameter(1, email);
			personne = (Personne) query.getSingleResult ();

		} catch (NoResultException e) {
		logger.info("email non trouve", e);
		return null;
		}
		return personne;
		}

			 */
	/*@Transactional
    @Modifying
	public void insertPersonneWithQuery(Personne personne) {
		// sessionImplementor.getEntityPersister()
		// Serializable id = persister.getIdentifier(personne);
		// System.out.println("id " +id);
		System.out.println("PersonneRepositoryAddMethods idpersonne " +personne.getIdpersonne());
		System.out.println("PersonneRepositoryAddMethods email " +personne.getEmail());
		System.out.println("PersonneRepositoryAddMethods mot de passe " +personne.getMotDePasse());
		System.out.println("PersonneRepositoryAddMethods nom " +personne.getNom());
		System.out.println("PersonneRepositoryAddMethods description" +personne.getDescription());
		entityManager
				.createNativeQuery(
						"INSERT INTO personne (idPersonne, email, motDePasse,nom,description) VALUES (?,?,?,?,?)")
				.setParameter(1, personne.getIdpersonne())
				.setParameter(2, personne.getEmail())
				.setParameter(3, personne.getMotDePasse())
				.setParameter(4, personne.getNom())			
				.setParameter(5, personne.getDescription()).executeUpdate();
		
	}*/
	
	
	
}
