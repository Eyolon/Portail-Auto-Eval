-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.8.2-beta
-- PostgreSQL version: 9.4
-- Project Site: pgmodeler.com.br
-- Model Author: ---


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: "portail-auto-eval" | type: DATABASE --
-- -- DROP DATABASE IF EXISTS "portail-auto-eval";
-- CREATE DATABASE "portail-auto-eval"
-- 	ENCODING = 'UTF8'
-- 	LC_COLLATE = 'French_France.UTF8'
-- 	LC_CTYPE = 'French_France.UTF8'
-- 	TABLESPACE = pg_default
-- 	OWNER = postgres
-- ;
-- -- ddl-end --
-- 

-- object: public.utilisateur_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.utilisateur_id_seq CASCADE;
CREATE SEQUENCE public.utilisateur_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.utilisateur_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.utilisateur | type: TABLE --
-- DROP TABLE IF EXISTS public.utilisateur CASCADE;
CREATE TABLE public.utilisateur(
	id bigint NOT NULL DEFAULT nextval('public.utilisateur_id_seq'::regclass),
	id_type_utilisateur bigint NOT NULL,
	id_connexion bigint NOT NULL,
	login character varying(20),
	email character varying(50),
	id_service bigint,
	CONSTRAINT prk_constraint_utilisateur PRIMARY KEY (id),
	CONSTRAINT uq_utilisateur_login UNIQUE (login),
	CONSTRAINT uq_utilisateur_email UNIQUE (email)

);
-- ddl-end --
ALTER TABLE public.utilisateur OWNER TO postgres;
-- ddl-end --

-- object: public.type_utilisateur_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.type_utilisateur_id_seq CASCADE;
CREATE SEQUENCE public.type_utilisateur_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.type_utilisateur_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.type_utilisateur | type: TABLE --
-- DROP TABLE IF EXISTS public.type_utilisateur CASCADE;
CREATE TABLE public.type_utilisateur(
	id bigint NOT NULL DEFAULT nextval('type_d_utilisateur_id_seq'::regclass),
	libelle character varying(25),
	CONSTRAINT pk_type_utilisateur_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.type_utilisateur OWNER TO postgres;
-- ddl-end --

-- object: public.connexion_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.connexion_id_seq CASCADE;
CREATE SEQUENCE public.connexion_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.connexion_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.connexion | type: TABLE --
-- DROP TABLE IF EXISTS public.connexion CASCADE;
CREATE TABLE public.connexion(
	id bigint NOT NULL DEFAULT nextval('public.connexion_id_seq'::regclass),
	motdepasse character varying,
	CONSTRAINT prk_constraint_connexion PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.connexion OWNER TO postgres;
-- ddl-end --

-- object: public.service_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.service_id_seq CASCADE;
CREATE SEQUENCE public.service_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.service_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.service | type: TABLE --
-- DROP TABLE IF EXISTS public.service CASCADE;
CREATE TABLE public.service(
	id bigint NOT NULL DEFAULT nextval('public.service_id_seq'::regclass),
	libelle character varying(25),
	CONSTRAINT pk_service_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.service OWNER TO postgres;
-- ddl-end --

-- object: public.formulaire_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.formulaire_id_seq CASCADE;
CREATE SEQUENCE public.formulaire_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.formulaire_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.formulaire | type: TABLE --
-- DROP TABLE IF EXISTS public.formulaire CASCADE;
CREATE TABLE public.formulaire(
	id bigint NOT NULL DEFAULT nextval('public.formulaire_id_seq'::regclass),
	nom character varying(25),
	CONSTRAINT pk_formulaire PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.formulaire OWNER TO postgres;
-- ddl-end --

-- object: public.question_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.question_id_seq CASCADE;
CREATE SEQUENCE public.question_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.question_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.question | type: TABLE --
-- DROP TABLE IF EXISTS public.question CASCADE;
CREATE TABLE public.question(
	id bigint NOT NULL DEFAULT nextval('public.question_id_seq'::regclass),
	valeur character varying(25),
	id_formulaire bigint,
	id_critere bigint,
	CONSTRAINT pk_question_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.question OWNER TO postgres;
-- ddl-end --

-- object: public.critere_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.critere_id_seq CASCADE;
CREATE SEQUENCE public.critere_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.critere_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.critere | type: TABLE --
-- DROP TABLE IF EXISTS public.critere CASCADE;
CREATE TABLE public.critere(
	id bigint NOT NULL DEFAULT nextval('public.critere_id_seq'::regclass),
	nom character varying(20),
	id_categorie bigint,
	CONSTRAINT pk_critere_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.critere OWNER TO postgres;
-- ddl-end --

-- object: public.categorie_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.categorie_id_seq CASCADE;
CREATE SEQUENCE public.categorie_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.categorie_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.categorie | type: TABLE --
-- DROP TABLE IF EXISTS public.categorie CASCADE;
CREATE TABLE public.categorie(
	id bigint NOT NULL DEFAULT nextval('public.categorie_id_seq'::regclass),
	nom character varying(20),
	CONSTRAINT pk_categorie_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.categorie OWNER TO postgres;
-- ddl-end --

-- object: public.note_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.note_id_seq CASCADE;
CREATE SEQUENCE public.note_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
ALTER SEQUENCE public.note_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.note | type: TABLE --
-- DROP TABLE IF EXISTS public.note CASCADE;
CREATE TABLE public.note(
	id bigint NOT NULL DEFAULT nextval('public.note_id_seq'::regclass),
	valeur integer,
	remarque character varying(200),
	id_question bigint,
	id_utilisateur bigint,
	CONSTRAINT pk_note_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.note OWNER TO postgres;
-- ddl-end --

-- object: fk_utilisateur_id_connexion | type: CONSTRAINT --
-- ALTER TABLE public.utilisateur DROP CONSTRAINT IF EXISTS fk_utilisateur_id_connexion CASCADE;
ALTER TABLE public.utilisateur ADD CONSTRAINT fk_utilisateur_id_connexion FOREIGN KEY (id_connexion)
REFERENCES public.connexion (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_utilisateur_id_service | type: CONSTRAINT --
-- ALTER TABLE public.utilisateur DROP CONSTRAINT IF EXISTS fk_utilisateur_id_service CASCADE;
ALTER TABLE public.utilisateur ADD CONSTRAINT fk_utilisateur_id_service FOREIGN KEY (id_service)
REFERENCES public.service (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_utilisateur_id_type_utilisateur | type: CONSTRAINT --
-- ALTER TABLE public.utilisateur DROP CONSTRAINT IF EXISTS fk_utilisateur_id_type_utilisateur CASCADE;
ALTER TABLE public.utilisateur ADD CONSTRAINT fk_utilisateur_id_type_utilisateur FOREIGN KEY (id_type_utilisateur)
REFERENCES public.type_utilisateur (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_question_id_formulaire | type: CONSTRAINT --
-- ALTER TABLE public.question DROP CONSTRAINT IF EXISTS fk_question_id_formulaire CASCADE;
ALTER TABLE public.question ADD CONSTRAINT fk_question_id_formulaire FOREIGN KEY (id_formulaire)
REFERENCES public.formulaire (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_question_id_critere | type: CONSTRAINT --
-- ALTER TABLE public.question DROP CONSTRAINT IF EXISTS fk_question_id_critere CASCADE;
ALTER TABLE public.question ADD CONSTRAINT fk_question_id_critere FOREIGN KEY (id_critere)
REFERENCES public.critere (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_critere_id_categorie | type: CONSTRAINT --
-- ALTER TABLE public.critere DROP CONSTRAINT IF EXISTS fk_critere_id_categorie CASCADE;
ALTER TABLE public.critere ADD CONSTRAINT fk_critere_id_categorie FOREIGN KEY (id_categorie)
REFERENCES public.categorie (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_note_id_question | type: CONSTRAINT --
-- ALTER TABLE public.note DROP CONSTRAINT IF EXISTS fk_note_id_question CASCADE;
ALTER TABLE public.note ADD CONSTRAINT fk_note_id_question FOREIGN KEY (id_question)
REFERENCES public.question (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk_note_id_utilisateur | type: CONSTRAINT --
-- ALTER TABLE public.note DROP CONSTRAINT IF EXISTS fk_note_id_utilisateur CASCADE;
ALTER TABLE public.note ADD CONSTRAINT fk_note_id_utilisateur FOREIGN KEY (id_utilisateur)
REFERENCES public.utilisateur (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --