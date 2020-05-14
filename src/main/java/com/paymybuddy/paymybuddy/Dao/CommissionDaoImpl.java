package com.paymybuddy.paymybuddy.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.model.Commission;
import com.paymybuddy.paymybuddy.model.OperationCompte;
import com.paymybuddy.paymybuddy.repository.CommissionRepository;

@Repository
public class CommissionDaoImpl implements CommissionDao {

	Logger logger = LoggerFactory.getLogger(CommissionDaoImpl.class);
	
	@Autowired
	CommissionRepository commissionRepository;
	private OperationCompte operationCompte;
	private Double montant;
	private Double taux;
	Commission commission;
	

	@Override
	public void creerCommission(OperationCompte operationCompte, Double montant, Double taux) {
		this.operationCompte = operationCompte;
		this.montant = montant;
		this.taux = taux;
		
		commission = new Commission();
		
		commission.setMontant(montant);
		commission.setTaux(taux);
		commission.setOperationCompte(operationCompte);
		commissionRepository.save(commission);
		logger.info("Commission créée");
		
	}

}
