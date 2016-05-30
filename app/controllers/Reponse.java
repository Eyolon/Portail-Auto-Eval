package controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.NoteDAO;
import hibernate.dao.QuestionDAO;
import hibernate.dao.ServiceDAO;
import hibernate.dao.UtilisateurDAO;
import hibernate.model.Formulaire;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Service;
import hibernate.model.Utilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Reponse extends Controller{

	
	public static Promise<Result> insertReponses() {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
						/*Il aurait été plus rapide de mapper le json en objet mais j'y suis pas parvenus
						 * du coup : TO DO
						 * 
						 * */		
					//TO DO retirer le new Note() et voir s'il existe dans la BDD avant -> nope, voir sms sur l'historisation de réponses
					Note n = new Note();
					
					Utilisateur u = null;
					Question q = null;
					//RECUPERE JUSTE l'ID de LA QUESTION ET DE l'UTILISATEUR = plus léger et ça utilise LES OBJETS !!!
					//Y'a vraiment une majuscule en DEBUT d'attribut ? A refactorer en "utilisateur" à cours terme -> ok
					//Utiliser le token pour récupérer l'utilisateur ...
					if(jsonN.has("utilisateur") && jsonN.get("utilisateur").has("id")) {
						u = UtilisateurDAO.findById(jsonN.get("utilisateur").get("id").asLong());
					}
					if(jsonN.has("questionFull") && jsonN.get("questionFull").has("id")) {
						q = QuestionDAO.findById(jsonN.get("questionFull").get("id").asLong());
					}
					//Tester s'il y a déjà une note pour cette question par cet utilisateur -> nope, voir sms sur l'historisation de réponses
					
					if(jsonN.get("questionFull").get("notes").get(0).get("valeur") != null) {
						n.setValeur(jsonN.get("questionFull").get("notes").get(0).get("valeur").asInt());
					}
					if(jsonN.get("questionFull").get("notes").get(0).get("remarque") != null) {
						n.setRemarque(jsonN.get("questionFull").get("notes").get(0).get("remarque").toString());
					}
					n.setUtilisateur(u);
					n.setQuestion(q);
					
					if(n.getUtilisateur() != null && n.getQuestion() != null){
						n.setDateSaisie(Instant.now());
						NoteDAO.insert(n);
						BDDUtils.commit(isActive, tx);
						return ok();
					}
						
					} catch(HibernateException ex) {
						Logger.error("Hibernate failure : "+ ex.getMessage());
						BDDUtils.rollback(isActive, tx);
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
				}
			return notFound();
		});
	}
	
	public static Promise<Result> getReponsesPerEtablissement(Long idEtablissement){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			
			JSONObject ja = new JSONObject();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				List<Long> le = new ArrayList<>();
				le.add(idEtablissement);
				
				List<Long> ls = new ArrayList<>();
				
				List<Service> listService = ServiceDAO.getAllServiceByEtablissement(idEtablissement);
				listService.stream().mapToLong((e) -> e.getId()).forEach(ls::add);
				
				ja.put("listService", ConstructJSONObjects.getJSONArrayforListServices(listService));
				JSONObject jsonFormulaire = new JSONObject();
				JSONObject jsonQuestion = new JSONObject();
				for (Service service : listService) {
					List<Long> listServiceId = new ArrayList<>();
					listServiceId.add(service.getId());
					
					List<Formulaire> listFormulaire = FormulaireDAO.getAllServiceByEtablissement(le, listServiceId);
					jsonFormulaire.put(Long.toString(service.getId()), ConstructJSONObjects.getJSONArrayforListFormulairesFull(listFormulaire));
				
					for (Formulaire formulaire : listFormulaire) {
						jsonQuestion.put(Long.toString(formulaire.getId()), ConstructJSONObjects.getJSONArrayforListQuestions(QuestionDAO.getListQuestionByFormulaireId(formulaire.getId())));
					}
				}
				
				ja.put("listFormulaire", jsonFormulaire);
				ja.put("listQuestion", jsonQuestion);
				if(listService != null && !listService.isEmpty()) {
					ja.put("listNote", ConstructJSONObjects.getJSONArrayforNoteWithQuestion(NoteDAO.getListNoteByEtablissementsAndServices(le, ls)));
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
		return promiseOfResult;
	}
}
