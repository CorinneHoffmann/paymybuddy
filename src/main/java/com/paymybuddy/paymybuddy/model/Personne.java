package com.paymybuddy.paymybuddy.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

@Entity
@Table(name="PERSONNE",uniqueConstraints = {
	      @UniqueConstraint(columnNames = "email", name = "uniqueEmailConstraint")})
public class Personne {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpersonne")
	private Long idPersonne;
	
	@NotNull
    @Size(max = 80)
	@Column(name = "email")
	private String email;
	
	@NotNull
    @Size(max = 20)
	@Column(name = "motdepasse")
	private String motDePasse;
	
	@NotNull
    @Size(max = 40)
	@Column(name = "nom")
	private String nom;
	
	@NotNull
    @Size(max = 80)
	@Column(name = "description")
	private String description;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Personne> amis;
	
	@OneToOne(mappedBy = "compte", cascade = CascadeType.ALL)

	public Long getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(Long idPersonne) {
		this.idPersonne = idPersonne;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Personne> getAmis() {
		return amis;
	}

	public void setAmis(List<Personne> amis) {
		this.amis = amis;
	}
	
}
