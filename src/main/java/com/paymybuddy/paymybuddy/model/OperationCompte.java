package com.paymybuddy.paymybuddy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.paymybuddy.paymybuddy.constants.SensComptable;
import com.paymybuddy.paymybuddy.constants.TypeOperation;
import com.sun.istack.NotNull;

@Entity
@Table(name = "OPERATIONCOMPTE")
public class OperationCompte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idoperationcompte")
	private Long idOperationCompte;
	
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
	@Column(name = "typeoperation")
	@Enumerated(EnumType.STRING)
	private TypeOperation typeOperation;
	
	@NotNull
	@Column(name = "debitcredit")
	@Enumerated(EnumType.STRING)
	private SensComptable debitCredit;
	
	@OneToOne
	@JoinColumn(name = "personneid")
	private Personne personne;
	
	@OneToOne
	@JoinColumn(name = "comptebancaireid")
	private CompteBancaire compteBancaire;

	public Long getIdOperationCompte() {
		return idOperationCompte;
	}

	public void setIdOperationCompte(Long idOperationCompte) {
		this.idOperationCompte = idOperationCompte;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/*
	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}*/
	
	public Date getDateOperation() {
		if (dateOperation == null) {
			return null;
		} else {
			return new Date(dateOperation.getTime());
		}
	}
	
	public void setDateOperation(Date dateOperation) {
		if (dateOperation == null)
			this.dateOperation = null;
		else
		this.dateOperation = new Date(dateOperation.getTime());
	
	}

	public TypeOperation getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(TypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}

	public SensComptable getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(SensComptable debitCredit) {
		this.debitCredit = debitCredit;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public CompteBancaire getCompteBancaire() {
		return compteBancaire;
	}

	public void setCompteBancaire(CompteBancaire compteBancaire) {
		this.compteBancaire = compteBancaire;
	}
	
	
	


}
