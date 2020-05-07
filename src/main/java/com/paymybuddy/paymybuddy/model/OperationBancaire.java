package com.paymybuddy.paymybuddy.model;

import java.util.Date;

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
@Table(name = "OPERATIONBANCAIRE")
public class OperationBancaire {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idoperationbancaire")
	private Long idOperationBancaire;
	
	@OneToOne
	@JoinColumn(name = "compteid")
	private Compte compte;

	@NotNull
	@Column(name = "montant")
	private Double montant;
	
	@NotNull
	@Column(name = "dateoperation")
	private Date dateOperation;
	
	@NotNull
    @Size(max = 10)
	@Column(name = "typeoperation")
	private String typeOperation;
	
	@NotNull
    @Size(max = 1)
	@Column(name = "debitcredit")
	private String debitCredit;
	
	@OneToOne
	@JoinColumn(name = "personneid")
	private Personne personne;
	
	@OneToOne
	@JoinColumn(name = "comptebancaireid")
	private CompteBancaire compteBancaire;
	
	
	public Long getIdOperationBancaire() {
		return idOperationBancaire;
	}

	public void setIdOperationBancaire(Long idOperationBancaire) {
		this.idOperationBancaire = idOperationBancaire;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public CompteBancaire getCompteBancaire() {
		return compteBancaire;
	}

	public void setCompteBancaire(CompteBancaire compteBancaire) {
		this.compteBancaire = compteBancaire;
	}


}
