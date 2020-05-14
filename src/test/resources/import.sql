
-- chargement personne
insert into personne(idpersonne,email,motDePasse,nom,description)values(1,'testmathiasdupont.@yahoo.fr','testmathias123','testdupont','librairie livraison');
insert into personne(idpersonne,email,motDePasse,nom,description)values(2,'testcorinne93.@gmail.com','testcoco93','testalicantour','restauration amigos');
insert into personne(idpersonne,email,motDePasse,nom,description)values(3,'testtitidavant.@gmail.com','testtiti456','testtitinom','insformatique depannage');
insert into personne(idpersonne,email,motDePasse,nom,description)values(4,'testchristineleglo.@yahoo.fr','testchristine698','testgloaguen','dessinatrice sur soie');
insert into personne(idpersonne,email,motDePasse,nom,description)values(5,'teststephanpunk.@hotmail.fr','testpunk78','testsidvocious','musicien');

-- chargement compte
insert into compte(idcompte,solde,personneid)values(1,100.50,1);
insert into compte(idcompte,solde,personneid)values(2,1531.25,2);
insert into compte(idcompte,solde,personneid)values(3,20.33,3);
insert into compte(idcompte,solde,personneid)values(4,50.00,4);

-- chargement amis
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(1,3);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(1,4);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(2,3);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(3,5);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(4,1);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(4,2);
insert into personne_amis(personne_idpersonne,amis_idpersonne)values(4,3);

-- chargement comptebancaire
insert into comptebancaire(idcomptebancaire,bic,iban,domiciliation,personneid)values(1,'FRLABGHY','FR12 ATRE FGT1 12KL POIU 12','SOCIETE GENERALE',1);
insert into comptebancaire(idcomptebancaire,bic,iban,domiciliation,personneid)values(2,'FRLABGJY','FR02 1860 17D1 12KL FGHJ 17','LA BANQUE POSTALE',2);

-- chargement operationcompte
insert into operationcompte(idoperationcompte,compteid,montant,dateoperation,typeoperation,debitcredit,personneid,comptebancaireid)values(1,2,100.20,'2020-01-10','PAIEMENT','D',3,NULL);
insert into operationcompte(idoperationcompte,compteid,montant,dateoperation,typeoperation,debitcredit,personneid,comptebancaireid)values(2,3,100.20,'2020-01-10','PAIEMENT','C',2,NULL);
insert into operationcompte(idoperationcompte,compteid,montant,dateoperation,typeoperation,debitcredit,personneid,comptebancaireid)values(3,1,300,'2020-02-15','VIREMENT','D',NULL,1);
insert into operationcompte(idoperationcompte,compteid,montant,dateoperation,typeoperation,debitcredit,personneid,comptebancaireid)values(4,3,500,'2020-02-15','VERSEMENT','C',NULL,NULL);

-- chargement commission
insert into commission(idcommission,taux,montant,operationcompteid)values(1,0.50,0.51,1);


