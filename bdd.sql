-- Table: categorie

-- DROP TABLE categorie;

CREATE TABLE categorie
(
  id bigserial NOT NULL,
  nom character varying(20),
  CONSTRAINT pk_categorie_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE categorie
  OWNER TO postgres;

-- Table: connexion

-- DROP TABLE connexion;

CREATE TABLE connexion
(
  id bigserial NOT NULL,
  motdepasse character varying,
  CONSTRAINT prk_constraint_connexion PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE connexion
  OWNER TO postgres;

-- Table: critere

-- DROP TABLE critere;

CREATE TABLE critere
(
  id bigserial NOT NULL,
  nom character varying(20),
  id_categorie bigint,
  CONSTRAINT pk_critere_id PRIMARY KEY (id),
  CONSTRAINT fk_critere_id_categorie FOREIGN KEY (id_categorie)
      REFERENCES categorie (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE critere
  OWNER TO postgres;

-- Table: formulaire

-- DROP TABLE formulaire;

CREATE TABLE formulaire
(
  id bigserial NOT NULL,
  nom character varying(25),
  CONSTRAINT pk_formulaire PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE formulaire
  OWNER TO postgres;

-- Table: question

-- DROP TABLE question;

CREATE TABLE question
(
  id bigserial NOT NULL,
  valeur character varying(25),
  id_formulaire bigint,
  id_critere bigint,
  CONSTRAINT pk_question_id PRIMARY KEY (id),
  CONSTRAINT fk_question_id_critere FOREIGN KEY (id_critere)
      REFERENCES critere (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_question_id_formulaire FOREIGN KEY (id_formulaire)
      REFERENCES formulaire (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE question
  OWNER TO postgres;

-- Table: service

-- DROP TABLE service;

CREATE TABLE service
(
  id bigserial NOT NULL,
  libelle character varying(25),
  CONSTRAINT pk_service_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE service
  OWNER TO postgres;

-- Table: type_utilisateur

-- DROP TABLE type_utilisateur;

CREATE TABLE type_utilisateur
(
  id bigserial NOT NULL,
  libelle character varying(25),
  CONSTRAINT pk_type_utilisateur_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE type_utilisateur
  OWNER TO postgres;

-- Table: utilisateur

-- DROP TABLE utilisateur;

CREATE TABLE utilisateur
(
  id bigserial NOT NULL,
  id_type_utilisateur bigint NOT NULL,
  id_connexion bigint NOT NULL,
  login character varying(20),
  id_service bigint,
  CONSTRAINT prk_constraint_utilisateur PRIMARY KEY (id),
  CONSTRAINT fk_utilisateur_id_connexion FOREIGN KEY (id_connexion)
      REFERENCES connexion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_utilisateur_id_service FOREIGN KEY (id_service)
      REFERENCES service (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_utilisateur_id_type_utilisateur FOREIGN KEY (id_type_utilisateur)
      REFERENCES type_utilisateur (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_utilisateur_login UNIQUE (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE utilisateur
  OWNER TO postgres;

-- Table: note

-- DROP TABLE note;

CREATE TABLE note
(
  id bigserial NOT NULL,
  valeur integer,
  remarque character varying(200),
  id_question bigint,
  id_utilisateur bigint,
  CONSTRAINT pk_note_id PRIMARY KEY (id),
  CONSTRAINT fk_note_id_question FOREIGN KEY (id_question)
      REFERENCES question (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_note_id_utilisateur FOREIGN KEY (id_utilisateur)
      REFERENCES utilisateur (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE note
  OWNER TO postgres;

-- Table: formulaire_service

-- DROP TABLE formulaire_service;

CREATE TABLE formulaire_service
(
  id_formulaire bigint NOT NULL,
  id_service bigint NOT NULL,
  id_type_utilisateur bigint NOT NULL,
  CONSTRAINT pk_formulaire_service_id PRIMARY KEY (id_formulaire, id_service, id_type_utilisateur),
  CONSTRAINT fk_formulaire_service_id_formulaire FOREIGN KEY (id_formulaire)
      REFERENCES formulaire (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_formulaire_service_id_service FOREIGN KEY (id_service)
      REFERENCES service (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_formulaire_service_id_type_utilisateur FOREIGN KEY (id_type_utilisateur)
      REFERENCES type_utilisateur (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE formulaire_service
  OWNER TO postgres;
