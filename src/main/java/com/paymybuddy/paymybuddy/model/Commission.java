package com.paymybuddy.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "COMMISSION")
public class Commission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcommission")
	private Long idCommission;
	
	@NotNull
	@Column(name = "taux")
	private Double taux;
	
	@NotNull
	@Column(name = "montant")
	private Double montant;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "operationcompteid")
	private OperationCompte operationCompte;

	public Long getIdCommission() {
		return idCommission;
	}

	public void setIdCommission(Long idCommission) {
		this.idCommission = idCommission;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public OperationCompte getOperationCompte() {
		return operationCompte;
	}

	public void setOperationCompte(OperationCompte operationCompte) {
		this.operationCompte = operationCompte;
	}
	
}
