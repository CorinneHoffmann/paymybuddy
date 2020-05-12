package com.paymybuddy.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.CompteBancaire;

@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {

}
