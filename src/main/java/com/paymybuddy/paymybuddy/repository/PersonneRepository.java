package com.paymybuddy.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
	
}
