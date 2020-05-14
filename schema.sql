use paymybuddy;

DROP TABLE IF EXISTS commission;
DROP TABLE IF EXISTS operationcompte;
DROP TABLE IF EXISTS comptebancaire;
DROP TABLE IF EXISTS compte;
DROP TABLE IF EXISTS personne_amis;
DROP TABLE IF EXISTS personne;

CREATE TABLE personne (
  idpersonne bigint(20) NOT NULL AUTO_INCREMENT,
  email  varchar(80) NOT NULL,
  motdepasse varchar(20) NOT NULL,
  nom varchar(40) NOT NULL,
  description varchar(80) NOT NULL,
  PRIMARY KEY (idpersonne),
  CONSTRAINT UC_personne UNIQUE (email)
)
ENGINE=INNODB;

CREATE TABLE personne_amis (
  personne_idpersonne bigint(20) NOT NULL,
  amis_idpersonne bigint(20) NOT NULL,
  PRIMARY KEY (personne_idpersonne,amis_idpersonne),
  FOREIGN KEY (personne_idpersonne) REFERENCES personne(idpersonne),
  FOREIGN KEY (amis_idpersonne) REFERENCES personne(idpersonne)
)
ENGINE=INNODB;

CREATE TABLE comptebancaire (
  idcomptebancaire bigint(20) NOT NULL AUTO_INCREMENT,
  bic varchar(8) NOT NULL,
  iban varchar(27) NOT NULL,
  domiciliation varchar(40) NOT NULL,
  personneid bigint(20) NOT NULL,
  PRIMARY KEY (idcomptebancaire),
  FOREIGN KEY (personneid) REFERENCES personne(idpersonne)
)
ENGINE=INNODB;

CREATE TABLE compte (
  idcompte bigint(20) NOT NULL AUTO_INCREMENT,
  solde  double NOT NULL,
  personneid bigint(20) NOT NULL,
  PRIMARY KEY (idcompte),
  FOREIGN KEY (personneid) REFERENCES personne(idpersonne)
)
ENGINE=INNODB;

CREATE TABLE operationcompte (
  idoperationcompte bigint(20) NOT NULL AUTO_INCREMENT,
  dateoperation  date NOT NULL,
  debitcredit  varchar(1) NOT NULL,
  montant  double NOT NULL,
  typeoperation  varchar(10) NOT NULL,
  compteid bigint(20), 
  comptebancaireid bigint(20), 
  personneid bigint(20),
  PRIMARY KEY (idoperationcompte),
  FOREIGN KEY (compteid) REFERENCES compte(idcompte),
  FOREIGN KEY (comptebancaireid) REFERENCES comptebancaire(idcomptebancaire),
  FOREIGN KEY (personneid) REFERENCES personne(idpersonne)
)
ENGINE=INNODB;

CREATE TABLE commission (
  idcommission bigint(20) NOT NULL AUTO_INCREMENT,
  montant  double NOT NULL,
  taux  double NOT NULL,
  operationcompteid bigint(20), 
  PRIMARY KEY (idcommission),
  FOREIGN KEY (operationcompteid) REFERENCES operationcompte(idoperationcompte)
)
ENGINE=INNODB;
