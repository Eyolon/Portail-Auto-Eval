--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.0
-- Dumped by pg_dump version 9.5.0

-- Started on 2016-04-22 21:58:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 199 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 199
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 24841)
-- Name: categorie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE categorie (
    id bigint NOT NULL,
    nom character varying(25)
);


ALTER TABLE categorie OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 24839)
-- Name: categorie_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE categorie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE categorie_id_seq OWNER TO postgres;

--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 180
-- Name: categorie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE categorie_id_seq OWNED BY categorie.id;


--
-- TOC entry 183 (class 1259 OID 24849)
-- Name: connexion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE connexion (
    id bigint NOT NULL,
    motdepasse character varying
);


ALTER TABLE connexion OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 24847)
-- Name: connexion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE connexion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE connexion_id_seq OWNER TO postgres;

--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 182
-- Name: connexion_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE connexion_id_seq OWNED BY connexion.id;


--
-- TOC entry 185 (class 1259 OID 24860)
-- Name: critere; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE critere (
    id bigint NOT NULL,
    nom character varying(30),
    id_categorie bigint
);


ALTER TABLE critere OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 24858)
-- Name: critere_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE critere_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE critere_id_seq OWNER TO postgres;

--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 184
-- Name: critere_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE critere_id_seq OWNED BY critere.id;


--
-- TOC entry 187 (class 1259 OID 24873)
-- Name: formulaire; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE formulaire (
    id bigint NOT NULL,
    nom character varying(25)
);


ALTER TABLE formulaire OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 24871)
-- Name: formulaire_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE formulaire_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE formulaire_id_seq OWNER TO postgres;

--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 186
-- Name: formulaire_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE formulaire_id_seq OWNED BY formulaire.id;


--
-- TOC entry 198 (class 1259 OID 24956)
-- Name: formulaire_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE formulaire_service (
    id_formulaire bigint NOT NULL,
    id_service bigint NOT NULL,
    id_type_utilisateur bigint NOT NULL
);


ALTER TABLE formulaire_service OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 24940)
-- Name: note; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE note (
    id bigint NOT NULL,
    valeur integer,
    remarque character varying(200),
    id_question bigint,
    id_utilisateur bigint,
    date_saisie timestamp without time zone
);


ALTER TABLE note OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 24938)
-- Name: note_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE note_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE note_id_seq OWNER TO postgres;

--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 196
-- Name: note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE note_id_seq OWNED BY note.id;


--
-- TOC entry 189 (class 1259 OID 24881)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE question (
    id bigint NOT NULL,
    valeur character varying(200),
    id_formulaire bigint,
    id_critere bigint
);


ALTER TABLE question OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 24879)
-- Name: question_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE question_id_seq OWNER TO postgres;

--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 188
-- Name: question_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE question_id_seq OWNED BY question.id;


--
-- TOC entry 191 (class 1259 OID 24899)
-- Name: service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE service (
    id bigint NOT NULL,
    libelle character varying(25)
);


ALTER TABLE service OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 24897)
-- Name: service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE service_id_seq OWNER TO postgres;

--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 190
-- Name: service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE service_id_seq OWNED BY service.id;


--
-- TOC entry 193 (class 1259 OID 24907)
-- Name: type_utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE type_utilisateur (
    id bigint NOT NULL,
    libelle character varying(25)
);


ALTER TABLE type_utilisateur OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 24905)
-- Name: type_utilisateur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE type_utilisateur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE type_utilisateur_id_seq OWNER TO postgres;

--
-- TOC entry 2235 (class 0 OID 0)
-- Dependencies: 192
-- Name: type_utilisateur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE type_utilisateur_id_seq OWNED BY type_utilisateur.id;


--
-- TOC entry 195 (class 1259 OID 24915)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE utilisateur (
    id bigint NOT NULL,
    id_type_utilisateur bigint NOT NULL,
    id_connexion bigint NOT NULL,
    login character varying(20),
    id_service bigint
);


ALTER TABLE utilisateur OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 24913)
-- Name: utilisateur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE utilisateur_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE utilisateur_id_seq OWNER TO postgres;

--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 194
-- Name: utilisateur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE utilisateur_id_seq OWNED BY utilisateur.id;


--
-- TOC entry 2034 (class 2604 OID 24844)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categorie ALTER COLUMN id SET DEFAULT nextval('categorie_id_seq'::regclass);


--
-- TOC entry 2035 (class 2604 OID 24852)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY connexion ALTER COLUMN id SET DEFAULT nextval('connexion_id_seq'::regclass);


--
-- TOC entry 2036 (class 2604 OID 24863)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY critere ALTER COLUMN id SET DEFAULT nextval('critere_id_seq'::regclass);


--
-- TOC entry 2037 (class 2604 OID 24876)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire ALTER COLUMN id SET DEFAULT nextval('formulaire_id_seq'::regclass);


--
-- TOC entry 2042 (class 2604 OID 24943)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note ALTER COLUMN id SET DEFAULT nextval('note_id_seq'::regclass);


--
-- TOC entry 2038 (class 2604 OID 24884)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question ALTER COLUMN id SET DEFAULT nextval('question_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 24902)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY service ALTER COLUMN id SET DEFAULT nextval('service_id_seq'::regclass);


--
-- TOC entry 2040 (class 2604 OID 24910)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY type_utilisateur ALTER COLUMN id SET DEFAULT nextval('type_utilisateur_id_seq'::regclass);


--
-- TOC entry 2041 (class 2604 OID 24918)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur ALTER COLUMN id SET DEFAULT nextval('utilisateur_id_seq'::regclass);


--
-- TOC entry 2202 (class 0 OID 24841)
-- Dependencies: 181
-- Data for Name: categorie; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY categorie (id, nom) FROM stdin;
1	Stratégie
4	RH
2	Management
3	Prendre Soin
5	Finances
6	Gestion du SI
7	Achats-Responsabilitées
\.


--
-- TOC entry 2237 (class 0 OID 0)
-- Dependencies: 180
-- Name: categorie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('categorie_id_seq', 1, false);


--
-- TOC entry 2204 (class 0 OID 24849)
-- Dependencies: 183
-- Data for Name: connexion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY connexion (id, motdepasse) FROM stdin;
1	$2a$10$Yc/K5Tr6fjTZKPaBsYWr4.Js/72jJpSgZOhRTmRuPxUDwvuPAo676
2	$2a$10$Yc/K5Tr6fjTZKPaBsYWr4.Js/72jJpSgZOhRTmRuPxUDwvuPAo676
3	$2a$10$Yc/K5Tr6fjTZKPaBsYWr4.Js/72jJpSgZOhRTmRuPxUDwvuPAo676
\.


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 182
-- Name: connexion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('connexion_id_seq', 1, false);


--
-- TOC entry 2206 (class 0 OID 24860)
-- Dependencies: 185
-- Data for Name: critere; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY critere (id, nom, id_categorie) FROM stdin;
1	Stratégie	1
2	Pilotage	2
4	Sécurité	2
3	Vision	2
5	Prendre Soin	3
6	Gestion Personel	4
7	Paie	4
8	Relations tutelles	5
9	Tresorerie	5
10	Gestion du SI	6
11	Externalisation	7
12	Ouverture sur l'environnement	7
\.


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 184
-- Name: critere_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('critere_id_seq', 1, false);


--
-- TOC entry 2208 (class 0 OID 24873)
-- Dependencies: 187
-- Data for Name: formulaire; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY formulaire (id, nom) FROM stdin;
1	Stratégie
2	LeaderShip
3	Processus & Services
4	RH
5	Parteners & Ressources
\.


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 186
-- Name: formulaire_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('formulaire_id_seq', 1, false);


--
-- TOC entry 2219 (class 0 OID 24956)
-- Dependencies: 198
-- Data for Name: formulaire_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY formulaire_service (id_formulaire, id_service, id_type_utilisateur) FROM stdin;
2	1	2
1	1	2
3	2	1
4	2	1
5	2	1
1	3	3
2	3	3
3	3	3
4	3	3
5	3	3
\.


--
-- TOC entry 2218 (class 0 OID 24940)
-- Dependencies: 197
-- Data for Name: note; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY note (id, valeur, remarque, id_question, id_utilisateur, date_saisie) FROM stdin;
6	99	\N	1	1	2016-04-20 22:32:03.576
82	55	\N	6	1	2016-04-21 00:14:17.226
83	22	\N	19	1	2016-04-21 00:17:13.811
84	22	\N	2	1	2016-04-21 00:24:53.25
85	22	\N	3	1	2016-04-21 00:37:10.905
86	55	\N	4	4	2016-04-21 21:50:39.674
87	53	\N	5	4	2016-04-21 21:52:21.054
88	23	\N	4	1	2016-04-21 23:06:29.918
89	22	\N	2	4	2016-04-21 23:24:14.69
90	25	\N	5	1	2016-04-22 00:19:05.571
91	33	\N	3	4	2016-04-22 00:20:29.33
92	36	\N	6	4	2016-04-22 00:22:23.066
93	22	\N	1	4	2016-04-22 00:22:28.787
94	13	\N	18	4	2016-04-22 00:22:37.589
95	56	\N	18	1	2016-04-22 00:25:49.344
96	32	\N	17	1	2016-04-22 00:26:01.169
97	54	\N	16	1	2016-04-22 00:26:13.725
98	22	\N	15	1	2016-04-22 00:26:18.953
99	5	\N	7	1	2016-04-22 20:58:37.967
100	56	\N	8	1	2016-04-22 20:58:52.508
\.


--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 196
-- Name: note_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('note_id_seq', 100, true);


--
-- TOC entry 2210 (class 0 OID 24881)
-- Dependencies: 189
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY question (id, valeur, id_formulaire, id_critere) FROM stdin;
43	Comment garantissez-vous la maitrise des risques professionnels?	4	6
1	Quels sont les fondamentaux, les missions et les prestations de l'Ets?	1	1
2	Comment le projet d'établissement a-t-il été élaboré?	2	2
3	Les parties prenantes ont-elles contribuées ?	2	2
4	Avez-vous identifié les parties prenantes ? 	2	2
5	Avez-vous affiché les chartes d'accompagnement?	2	2
6	Par quels moyens les évolutions environnementale sont-elle anticipées?	2	2
7	Quelles instances vous permettent d'apprécier les résultats ?	2	2
8	L'organisation de l'EHPAD est-elle régulièrement actualisée en fonction des résultats et des enjeux?	2	2
9	Comment associez-vous les acteurs à l'amélioration des pratiques?	2	2
44	Chacun dispose-t-il d'une fiche de poste à laquelle se référer?	4	6
12	Comment pilotez-vous la structure?	2	2
10	Comment la politique qualité contribue-t-elle à garantir les objectifs de votre etablissement?	2	2
11	Quelles sont les actions mises en place suites aux démarches évaluatives(interne et externe)?	2	2
13	Les cadres sont-ils impliqués dans l'auto-évaluation des fondamentaux de votre système de management ? 	2	3
14	Avez-vous identifié l'ensemble des processus permettant de remplir vos missions?	2	3
15	Quelle est la place de la culture RSE dans le management?	2	3
16	Quels sont les outils qui vous permettent de garantir la sécurité des biens et des personnes?	2	4
17	Comment est assurée la maintenance du bâtiment et des équipements?	2	4
18	Pouvez-vous nous montrer le dernier PV des services vétérinaires et celui de la commission de sécurité?	2	4
19	Comment sont gérés et traités les évènements indésirables?	2	4
20	Pouvez-nous présenter la démarche et les actions mises en œuvre sur l'écoute des résidents?	3	5
21	Pourriez-vous montrer votre procédure de traitement des plaintes et réclamations?	3	5
22	Avez-vous le CR du dernier CVS à disposition?	3	5
23	le CR du dernier CVS est-il affiché?	3	5
24	Est-ce que les prestations rendue sont clairement décrites et communiquées, sont-elles passées en revues lors de chaque changement majeure?	3	5
25	Pouvez-vous me présenter un contrat de séjour? 	3	5
26	Pouvez-vous me transmettre le règlement de fonctionnement?	3	5
27	Pouvez-vous me présenter le livret d'accueil des résidents?	3	5
28	Les objectifs d'accompagnement par population cible sont-ils formalisés?	3	5
29	Pouvez-vous me présenter votre dernier RAAM?	3	5
30	Pouvez-vous me présenter le projet du prendre soins de l'etablissement?	3	5
31	Pouvez-vous me présenter la procédure lié au circuit du médicament?	3	5
32	Pouvez-vous me présenter un plan de soin individualisé?	3	5
33	Pouvez-vous me présenter un projet personnalisé?	3	5
34	Comment s'organise la démarche de co-construction des projets personnalisés?	3	5
35	Pouvez-vous décirer votre procédure d'admission?	3	5
36	Quel accompagnement des personnes en fin de vie?	3	5
37	Comment l'animation s'inscrit-elle dans l'accompagnement?	3	5
38	Comment les paramédicaux inscrivent-ils leurs interventions dans l'accompagnement ?	3	5
39	Quel management des collaborateurs?	4	6
40	Pouvez-vous me montrer le règlement intérieur du personnel?	4	6
41	Quels sont les pièces administratives demandées à l'embauche (ADELI, extrait de casier, etc.)?	4	6
42	Comment assurez-vous l'adhésion des collaborateurs aux valeurs de l'organisation?	4	6
45	Comment planifiez-vous les évolution professionnelles en terme de besoin de compétences?	4	6
46	Comment mesure-t-on l'écoute accordée aux collaborateurs?	4	6
47	Comment l'organisation garantit des conditions de travail respectueuses de la législation?	4	7
48	Est-ce que la performance globale de l'etablissement est pilotée et analysée?	5	8
49	Quels sont les documents nécessaire au renouvellement d'une CTP?	5	8
50	La gestion financière de l'EHPAD est-elle suivie?	5	9
51	Les systèmes d'informations vous permettent des sauvegardes des données de votre ets?	5	10
52	Disposez-vous de données de veille règlementaire?	5	10
53	Comment est pilotée la relation avec vos fournisseurs?	5	11
54	Pouvez-vous nous transmettre votre contrat avec votre prestataire linge, ménage et restauration?	5	11
55	Comment l'organisation favorise-t-elle des conditions de partenariats durable, de confiance, de respect et d'ouverture?	5	12
\.


--
-- TOC entry 2242 (class 0 OID 0)
-- Dependencies: 188
-- Name: question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('question_id_seq', 1, false);


--
-- TOC entry 2212 (class 0 OID 24899)
-- Dependencies: 191
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY service (id, libelle) FROM stdin;
1	Service Quantine
2	Responsable Etablissement
3	Supra Administrateur
\.


--
-- TOC entry 2243 (class 0 OID 0)
-- Dependencies: 190
-- Name: service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('service_id_seq', 1, false);


--
-- TOC entry 2214 (class 0 OID 24907)
-- Dependencies: 193
-- Data for Name: type_utilisateur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY type_utilisateur (id, libelle) FROM stdin;
1	administrateur
2	utilisateur
3	super_administrateur
\.


--
-- TOC entry 2244 (class 0 OID 0)
-- Dependencies: 192
-- Name: type_utilisateur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('type_utilisateur_id_seq', 2, true);


--
-- TOC entry 2216 (class 0 OID 24915)
-- Dependencies: 195
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY utilisateur (id, id_type_utilisateur, id_connexion, login, id_service) FROM stdin;
1	1	1	utilisateur	1
2	2	2	administrateur	2
3	3	3	supradministrateur	3
4	1	2	bobi	1
\.


--
-- TOC entry 2245 (class 0 OID 0)
-- Dependencies: 194
-- Name: utilisateur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('utilisateur_id_seq', 1, false);


--
-- TOC entry 2044 (class 2606 OID 24846)
-- Name: pk_categorie_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categorie
    ADD CONSTRAINT pk_categorie_id PRIMARY KEY (id);


--
-- TOC entry 2048 (class 2606 OID 24865)
-- Name: pk_critere_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY critere
    ADD CONSTRAINT pk_critere_id PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 24878)
-- Name: pk_formulaire; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire
    ADD CONSTRAINT pk_formulaire PRIMARY KEY (id);


--
-- TOC entry 2064 (class 2606 OID 24960)
-- Name: pk_formulaire_service_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT pk_formulaire_service_id PRIMARY KEY (id_formulaire, id_service, id_type_utilisateur);


--
-- TOC entry 2062 (class 2606 OID 24945)
-- Name: pk_note_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note
    ADD CONSTRAINT pk_note_id PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 24886)
-- Name: pk_question_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question_id PRIMARY KEY (id);


--
-- TOC entry 2054 (class 2606 OID 24904)
-- Name: pk_service_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY service
    ADD CONSTRAINT pk_service_id PRIMARY KEY (id);


--
-- TOC entry 2056 (class 2606 OID 24912)
-- Name: pk_type_utilisateur_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY type_utilisateur
    ADD CONSTRAINT pk_type_utilisateur_id PRIMARY KEY (id);


--
-- TOC entry 2046 (class 2606 OID 24857)
-- Name: prk_constraint_connexion; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY connexion
    ADD CONSTRAINT prk_constraint_connexion PRIMARY KEY (id);


--
-- TOC entry 2058 (class 2606 OID 24920)
-- Name: prk_constraint_utilisateur; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT prk_constraint_utilisateur PRIMARY KEY (id);


--
-- TOC entry 2060 (class 2606 OID 24922)
-- Name: uq_utilisateur_login; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT uq_utilisateur_login UNIQUE (login);


--
-- TOC entry 2084 (class 2606 OID 24983)
-- Name: fk1yjtphfvlm5luo0q03tyjqx2n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fk1yjtphfvlm5luo0q03tyjqx2n FOREIGN KEY (id_service) REFERENCES service(id);


--
-- TOC entry 2086 (class 2606 OID 24993)
-- Name: fk2v88pedc1gqnjhe98euv7p6ko; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fk2v88pedc1gqnjhe98euv7p6ko FOREIGN KEY (id_type_utilisateur) REFERENCES type_utilisateur(id);


--
-- TOC entry 2080 (class 2606 OID 25003)
-- Name: fk36tqo5th8abnwthnnkshoh9rq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note
    ADD CONSTRAINT fk36tqo5th8abnwthnnkshoh9rq FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id);


--
-- TOC entry 2076 (class 2606 OID 25028)
-- Name: fk6on13bvwl8svn2v3i9k0fn1vn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fk6on13bvwl8svn2v3i9k0fn1vn FOREIGN KEY (id_type_utilisateur) REFERENCES type_utilisateur(id);


--
-- TOC entry 2065 (class 2606 OID 24866)
-- Name: fk_critere_id_categorie; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY critere
    ADD CONSTRAINT fk_critere_id_categorie FOREIGN KEY (id_categorie) REFERENCES categorie(id);


--
-- TOC entry 2081 (class 2606 OID 24961)
-- Name: fk_formulaire_service_id_formulaire; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fk_formulaire_service_id_formulaire FOREIGN KEY (id_formulaire) REFERENCES formulaire(id);


--
-- TOC entry 2082 (class 2606 OID 24966)
-- Name: fk_formulaire_service_id_service; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fk_formulaire_service_id_service FOREIGN KEY (id_service) REFERENCES service(id);


--
-- TOC entry 2083 (class 2606 OID 24971)
-- Name: fk_formulaire_service_id_type_utilisateur; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fk_formulaire_service_id_type_utilisateur FOREIGN KEY (id_type_utilisateur) REFERENCES type_utilisateur(id);


--
-- TOC entry 2077 (class 2606 OID 24946)
-- Name: fk_note_id_question; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note
    ADD CONSTRAINT fk_note_id_question FOREIGN KEY (id_question) REFERENCES question(id);


--
-- TOC entry 2078 (class 2606 OID 24951)
-- Name: fk_note_id_utilisateur; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note
    ADD CONSTRAINT fk_note_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id);


--
-- TOC entry 2067 (class 2606 OID 24887)
-- Name: fk_question_id_critere; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_question_id_critere FOREIGN KEY (id_critere) REFERENCES critere(id);


--
-- TOC entry 2068 (class 2606 OID 24892)
-- Name: fk_question_id_formulaire; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_question_id_formulaire FOREIGN KEY (id_formulaire) REFERENCES formulaire(id);


--
-- TOC entry 2071 (class 2606 OID 24923)
-- Name: fk_utilisateur_id_connexion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fk_utilisateur_id_connexion FOREIGN KEY (id_connexion) REFERENCES connexion(id);


--
-- TOC entry 2072 (class 2606 OID 24928)
-- Name: fk_utilisateur_id_service; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fk_utilisateur_id_service FOREIGN KEY (id_service) REFERENCES service(id);


--
-- TOC entry 2073 (class 2606 OID 24933)
-- Name: fk_utilisateur_id_type_utilisateur; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fk_utilisateur_id_type_utilisateur FOREIGN KEY (id_type_utilisateur) REFERENCES type_utilisateur(id);


--
-- TOC entry 2066 (class 2606 OID 24978)
-- Name: fka9pl3fwwfeq0e452812vtmr6m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY critere
    ADD CONSTRAINT fka9pl3fwwfeq0e452812vtmr6m FOREIGN KEY (id_categorie) REFERENCES categorie(id);


--
-- TOC entry 2085 (class 2606 OID 24988)
-- Name: fked3b5h5dnr400w5iwwojhj4bq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formulaire_service
    ADD CONSTRAINT fked3b5h5dnr400w5iwwojhj4bq FOREIGN KEY (id_formulaire) REFERENCES formulaire(id);


--
-- TOC entry 2079 (class 2606 OID 24998)
-- Name: fkgyqktkt8uowfkfn9imtawdy4p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY note
    ADD CONSTRAINT fkgyqktkt8uowfkfn9imtawdy4p FOREIGN KEY (id_question) REFERENCES question(id);


--
-- TOC entry 2074 (class 2606 OID 25018)
-- Name: fkko8qx1g171q4bsnngcp0h7b0l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fkko8qx1g171q4bsnngcp0h7b0l FOREIGN KEY (id_connexion) REFERENCES connexion(id);


--
-- TOC entry 2070 (class 2606 OID 25013)
-- Name: fkkrm4pl952248tu7whccf1dfpc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fkkrm4pl952248tu7whccf1dfpc FOREIGN KEY (id_formulaire) REFERENCES formulaire(id);


--
-- TOC entry 2075 (class 2606 OID 25023)
-- Name: fklfxskgqpoklaymc17ge3a7if9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fklfxskgqpoklaymc17ge3a7if9 FOREIGN KEY (id_service) REFERENCES service(id);


--
-- TOC entry 2069 (class 2606 OID 25008)
-- Name: fkqe70qunrxxsaniento38qqhn6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fkqe70qunrxxsaniento38qqhn6 FOREIGN KEY (id_critere) REFERENCES critere(id);


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-04-22 21:58:19

--
-- PostgreSQL database dump complete
--

