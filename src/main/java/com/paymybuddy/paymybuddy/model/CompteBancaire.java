package com.paymybuddy.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(name = "COMPTEBANCAIRE")
public class CompteBancaire {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcomptebancaire")
	private Long idCompteBancaire;

	@NotNull
    @Size(max = 8)
	@Column(name = "bic")
	private String bic;
	
	@NotNull
    @Size(max = 27)
	@Column(name = "iban")
	private String iban;
	
	@NotNull
    @Size(max = 40)
	@Column(name = "domiciliation")
	private String domiciliation;
	
	@OneToOne
	@JoinColumn(name = "personneid")
	private Personne personne;
	
	
	public Long getIdCompteBancaire() {
		return idCompteBancaire;
	}

	public void setIdCompteBancaire(Long idCompteBancaire) {
		this.idCompteBancaire = idCompteBancaire;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getDomiciliation() {
		return domiciliation;
	}

	public void setDomiciliation(String domiciliation) {
		this.domiciliation = domiciliation;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

}
