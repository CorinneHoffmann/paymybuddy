package com.paymybuddy.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long>{

}
