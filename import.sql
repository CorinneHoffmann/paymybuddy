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

