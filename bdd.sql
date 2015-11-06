------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------



------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE public.Utilisateur(
        id                    bigserial  NOT NULL ,
        nom                   VARCHAR (30)  ,
        prenom                VARCHAR (30)  ,
        dateNaissance         DATE   ,
        adresse_rue           VARCHAR (250)  ,
		adresse_ville         VARCHAR (100)  ,
		adresse_code_postal   VARCHAR (5)  ,
        adresseMail           VARCHAR (25)  ,
        telephone             VARCHAR (10)   ,
        id_Type_d_utilisateur bigserial   ,
        id_Connexion          bigserial   ,
        id_Genre              bigserial   ,
        CONSTRAINT prk_constraint_Utilisateur PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Type d'utilisateur
------------------------------------------------------------
CREATE TABLE public.Type_d_utilisateur(
        id      bigserial  NOT NULL ,
        Libelle VARCHAR (25)  ,
        CONSTRAINT prk_constraint_Type_d_utilisateur PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Connexion
------------------------------------------------------------
CREATE TABLE public.Connexion(
        id             bigserial  NOT NULL ,
        motDePasse     VARCHAR ,
        CONSTRAINT prk_constraint_Connexion PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Genre
------------------------------------------------------------
CREATE TABLE public.Genre(
        id    bigserial  NOT NULL ,
        genre VARCHAR (25)  ,
        CONSTRAINT prk_constraint_Genre PRIMARY KEY (id)
)WITHOUT OIDS;

ALTER TABLE public.Utilisateur ADD CONSTRAINT FK_Utilisateur_id_Type_d_utilisateur FOREIGN KEY (id_Type_d_utilisateur) REFERENCES public.Type_d_utilisateur(id);
ALTER TABLE public.Utilisateur ADD CONSTRAINT FK_Utilisateur_id_Connexion FOREIGN KEY (id_Connexion) REFERENCES public.Connexion(id);
ALTER TABLE public.Utilisateur ADD CONSTRAINT FK_Utilisateur_id_Genre FOREIGN KEY (id_Genre) REFERENCES public.Genre(id);