package com.paymybuddy.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.OperationCompte;

@Repository
public interface OperationCompteRepository extends JpaRepository<OperationCompte, Long> {

}
