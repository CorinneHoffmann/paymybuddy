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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idcommission")
	private Long idCommission;
	
	@NotNull
	@Column(name = "taux")
	private Integer taux;
	
	@NotNull
	@Column(name = "montant")
	private Double montant;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "operationbancaireid")
	private OperationBancaire operationBancaire;
}
