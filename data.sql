-- chargement personne
insert into personne(idpersonne,email,motDePasse,nom,description)values(1,'mathiasdupont.@yahoo.fr','mathias123','dupont','librairie livraison');
insert into personne(idpersonne,email,motDePasse,nom,description)values(2,'corinne93.@gmail.com','coco93','alicantour','restauration amigos');
insert into personne(idpersonne,email,motDePasse,nom,description)values(3,'titidavant.@gmail.com','titi456','titinom','insformatique depannage');
insert into personne(idpersonne,email,motDePasse,nom,description)values(4,'christineleglo.@yahoo.fr','christine698','gloaguen','dessinatrice sur soie');
insert into personne(idpersonne,email,motDePasse,nom,description)values(5,'stephanpunk.@hotmail.fr','punk78','sidvocious','musicien');

-- chargement compte
insert into compte(idcompte,solde,personneid)values(1,100.50,1);
insert into compte(idcompte,solde,personneid)values(2,1531.25,1);
insert into compte(idcompte,solde,personneid)values(3,20.33,3);
insert into compte(idcompte,solde,personneid)values(4,50.00,5);

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